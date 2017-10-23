/**
	 * 
	 * @Title: productPdf
	 * @Description: 生成pdf并打包
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "项目管理", "项目一览", "电子合同", "生成" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "yuq")
	public Reply productPdf() {
		Map<String, Object> param = _getParameters();
		
		System.err.println(param.toString());
		String project_id = StringUtils.nullToString(param.get("PROJECT_ID"));
		String PRO_CODE = StringUtils.nullToString(param.get("PRO_CODE"));
		String pdf_type=StringUtils.nullToOther(param.get("PDF_TYPE"),"0");//默认生成标准合同，非套打合同
		
		PdfTemplateService service = new PdfTemplateService();
		
		// 1、根据项目编号查询项目对应的电子档案模板列表
		List<Map<String, Object>> l = service
				.getProjectPdfTemplateLists_new(project_id);
		if (l == null || l.size() == 0) {
			throw new ActionException("该项目无对应的电子合同模板，请联系管理员！");
		}
		
		//如果之前已经生成过合同先删除老版本   不删除不影响后续操作，但是产时间占有服务器存储容量
		if(StringUtils.isNotBlank(param.get("TPM_UUID"))){
			String TPM_UUID = StringUtils.nullToString(param.get("TPM_UUID"));
			File dir = new File((String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+TPM_UUID);
			service.deleteDir(dir);
			//删除历史版本参数
			service.deletePdfTemplateHistDataNew(param);
		}
		
		// System.out.println("获取项目电子档案明细："+PRO_CODE+"--------\n"+l);
		String uuid = service.generateShortUuid();
		for (Map<String, Object> m : l) {
			String tpm_code = StringUtils.nullToString(m.get("TPM_CODE"));
			String pdfTemplateName = StringUtils.nullToString(m.get("NAME"));
			Map<String, Object> acrobatContext = new HashMap<String, Object>();
			acrobatContext.put("PROJECT_ID", project_id);
			try {
				// System.out.println("当前-----------》"+tpm_code+"----->"+pdfTemplateName);
				// 2、一次生成电子合同
				String outputFilePath = service.producePdf_new(tpm_code,pdfTemplateName,pdf_type, acrobatContext, uuid);
				service.savePdfTemplateHistData(acrobatContext);
				// System.out.println("当前-----------》保存路径"+pdfTemplateName);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ActionException("合同生成类型："+tpm_code+"名字为："+pdfTemplateName+"异常，请联系管理员！");
			}
		}
		try {
			// 3、将合同打包
			String zipfile = service.zipPdf(project_id);
			// 4、挂接到项目主表
			service.updateProjectPdfInfo(project_id, zipfile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ActionException("合同生成异常，请联系管理员！");
		}
		return new ReplyAjax(true, "生成合同成功");
	}

/**
	 * @param uuid2 
	 * 
	 * @Title: producePdf 
	 * @Description: (PDF模板导出方法) 
	 * @param pdfTemplateName	模板  file表name like 
	 * @param acrobatContext	模板sql中对应的参数
	 * @throws IOException
	 * @throws DocumentException 
	 * @return String 
	 * @throws 
	 */
	public String producePdf_new(String tpm_code,String pdfTemplateName,String pdfType,Map<String,Object> acrobatContext, String uuid2) throws IOException, DocumentException{
		String paramStr = acrobatContext.toString();//传入sql中的变量  主要为： PROJECT_ID
		String uuid = StringUtils.nullToOther(uuid2, generateShortUuid());//流水号
		
		String inputFilePath = "";
		File fi = new File((String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+uuid);
		if(!fi.exists()) fi.mkdirs();
		String outputFile = (String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+uuid+"/"+tpm_code+"-"+pdfTemplateName.replaceAll(".pdf", "")+".pdf";//  返回的临时文件
		String key = "";
		String TEMPLATE_FILE_ID = "";

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("TPM_CODE", tpm_code);
		param.put("PDFTEMPLATENAME", pdfTemplateName);
		param.put("PDF_TYPE", pdfType);
		
		Map<String,Object> template = Dao.selectOne("SRV_AIF.getTmpIdByName", param);
		if(template == null || template.size() == 0 ){
			throw new ActionException("未找到相应模板，请检查配置{"+tpm_code+","+pdfTemplateName+","+pdfType+"}");
		}
		TEMPLATE_FILE_ID = StringUtils.nullToString(template.get("ID"));//文件ID
		key = StringUtils.nullToString(template.get("TPF_TPM_ID"));//模板ID
		inputFilePath = StringUtils.nullToString(template.get("PDF_PATH"));//文件路径
		String is_multi=StringUtils.nullToString(template.get("IS_MULTI"));
		String SQL=StringUtils.nullToString(template.get("SQL"));
		String SQL_FIELD=StringUtils.nullToString(template.get("SQL_FIELD"));
		
		//模板唯一标识码注入
		acrobatContext.put("UUID", uuid);
		acrobatContext.put("合同打印流水号", uuid);
		acrobatContext.put("合同流水号", uuid);
		
		if (StringUtils.isNotBlank(SQL) && StringUtils.isNotBlank(SQL_FIELD)) {
//			如果SQL有值，则根据SQL中值判断生成0到多份合同
			List<HashMap<String,Object>> listFile = executeSql(SQL,acrobatContext);
			String[] lstSqlKeys = SQL_FIELD.trim().toUpperCase().split(",");
			for (HashMap<String,Object> fillData : listFile) {
//				如果多个参数循环取值
				for(String SqlKey :lstSqlKeys){
					if (fillData != null && !fillData.isEmpty() && fillData.containsKey(SqlKey)) {
						acrobatContext.put(SqlKey, fillData.get(SqlKey));
					}
				}
				//取第一个参数值作为文件名后缀
				if("1".equals(is_multi)){
					outputFile=(String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+uuid+"/"+tpm_code+"-"+pdfTemplateName.replaceAll(".pdf", "")+"_"+fillData.get(lstSqlKeys[0])+".pdf";
				}
				//======================================================
				//注入数据 TODO
				getAcrobatContextNew(key,acrobatContext);
				logger.info("-----------"+acrobatContext);
				
//				根据参数值生成PDF
				fillFields(inputFilePath, acrobatContext, outputFile);		
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("TEMPLATE_FILE_NAME", pdfTemplateName);
				params.put("TEMPLATE_FILE_ID", TEMPLATE_FILE_ID);
				params.put("EXPORT_FILE_PATH", outputFile);
				params.put("PARAMS", paramStr);
				params.put("UUID", uuid);
				params.put("TEMPLATE_MAIN_ID", key);
				params.put("PROJECT_ID", acrobatContext.get("PROJECT_ID"));
				params.put("USER_NAME", Security.getUser()!=null ? Security.getUser().getName():"未登录");
				Dao.insert("SRV_AIF.insertPdfExpHist", params);
				
			}
		}else{
//			如果SQL 没有值则表示必须生成一份合同
			//注入数据 TODO
			getAcrobatContextNew(key,acrobatContext);
			logger.info("-----------"+acrobatContext);
			
//			根据参数值生成PDF
			fillFields(inputFilePath, acrobatContext, outputFile);		
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("TEMPLATE_FILE_NAME", pdfTemplateName);
			params.put("TEMPLATE_FILE_ID", TEMPLATE_FILE_ID);
			params.put("EXPORT_FILE_PATH", outputFile);
			params.put("PARAMS", paramStr);
			params.put("UUID", uuid);
			params.put("TEMPLATE_MAIN_ID", key);
			params.put("PROJECT_ID", acrobatContext.get("PROJECT_ID"));
			params.put("USER_NAME", Security.getUser()!=null ? Security.getUser().getName():"未登录");
			Dao.insert("SRV_AIF.insertPdfExpHist", params);
			
		}
		
		
		
		
			
			
		
		acrobatContext.put("PROJECT_ID",  acrobatContext.get("PROJECT_ID"));
		Dao.update("SRV_AIF.updateProjectPdfStatusInfo", acrobatContext);
		
		return outputFile;
	}



/**
	 * 
	* 导出合同套zip包
	 * @param name_paths 
	 * @throws IOException 
	* @date 2017年10月10日 下午1:51:47 
	 */
	public String zipPdfSeries(List<Map<String, Object>> pdfs, String seriesId) throws IOException  {
		String basePath = (String) SkyEye.getConfig().get("file.path.temp") + "/pdfTemplate/ExportZipPdfSeries/";
		//输入压缩的pdf模板文件夹路径
		String filePath = basePath + "series" + seriesId;
		
		//创建输入源文件对象
		File file = new File(filePath);
		if (file.exists()) ZipUtil.deleteDir(file);
		file.deleteOnExit();
		file.mkdirs();
		for (Map<String, Object> name_path : pdfs) {
			//pdf模板文件名
			String pdfName = StringUtils.nullToString(name_path.get("NAME"));
			//pdf模板文件路径
			String pdfPath = StringUtils.nullToString(name_path.get("PDF_PATH"));
			//pdf模板输入压缩源路径
			String newFilePath = filePath+"/"+pdfName.replaceAll(".pdf", "")+".pdf";
			file = new File(pdfPath);
			//将服务器存储的pdf模板拷贝到pdf模板输入压缩源路径下
			try {
				FileUtils.copyFile(file, new File(newFilePath));
			} catch (IOException e) {
				e.printStackTrace();
				throw new ActionException("类型为："+name_path.get("TPM_CODE") +"名为:"+pdfName+"合同模板不存在，请核实该模版路径是否正确！");
			}
		}
		//输出压缩包路径
		String zipfile = filePath+".zip";
		ZipUtil.zip(filePath, zipfile, "GBK");
		return zipfile;
	}