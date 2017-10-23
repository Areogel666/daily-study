/**
	 * 
	 * @Title: productPdf
	 * @Description: ����pdf�����
	 * @return Reply
	 * @throws
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "��Ŀ����", "��Ŀһ��", "���Ӻ�ͬ", "����" })
	@aDev(code = "6195", email = "yuq@strongflc.com", name = "yuq")
	public Reply productPdf() {
		Map<String, Object> param = _getParameters();
		
		System.err.println(param.toString());
		String project_id = StringUtils.nullToString(param.get("PROJECT_ID"));
		String PRO_CODE = StringUtils.nullToString(param.get("PRO_CODE"));
		String pdf_type=StringUtils.nullToOther(param.get("PDF_TYPE"),"0");//Ĭ�����ɱ�׼��ͬ�����״��ͬ
		
		PdfTemplateService service = new PdfTemplateService();
		
		// 1��������Ŀ��Ų�ѯ��Ŀ��Ӧ�ĵ��ӵ���ģ���б�
		List<Map<String, Object>> l = service
				.getProjectPdfTemplateLists_new(project_id);
		if (l == null || l.size() == 0) {
			throw new ActionException("����Ŀ�޶�Ӧ�ĵ��Ӻ�ͬģ�壬����ϵ����Ա��");
		}
		
		//���֮ǰ�Ѿ����ɹ���ͬ��ɾ���ϰ汾   ��ɾ����Ӱ��������������ǲ�ʱ��ռ�з������洢����
		if(StringUtils.isNotBlank(param.get("TPM_UUID"))){
			String TPM_UUID = StringUtils.nullToString(param.get("TPM_UUID"));
			File dir = new File((String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+TPM_UUID);
			service.deleteDir(dir);
			//ɾ����ʷ�汾����
			service.deletePdfTemplateHistDataNew(param);
		}
		
		// System.out.println("��ȡ��Ŀ���ӵ�����ϸ��"+PRO_CODE+"--------\n"+l);
		String uuid = service.generateShortUuid();
		for (Map<String, Object> m : l) {
			String tpm_code = StringUtils.nullToString(m.get("TPM_CODE"));
			String pdfTemplateName = StringUtils.nullToString(m.get("NAME"));
			Map<String, Object> acrobatContext = new HashMap<String, Object>();
			acrobatContext.put("PROJECT_ID", project_id);
			try {
				// System.out.println("��ǰ-----------��"+tpm_code+"----->"+pdfTemplateName);
				// 2��һ�����ɵ��Ӻ�ͬ
				String outputFilePath = service.producePdf_new(tpm_code,pdfTemplateName,pdf_type, acrobatContext, uuid);
				service.savePdfTemplateHistData(acrobatContext);
				// System.out.println("��ǰ-----------������·��"+pdfTemplateName);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ActionException("��ͬ�������ͣ�"+tpm_code+"����Ϊ��"+pdfTemplateName+"�쳣������ϵ����Ա��");
			}
		}
		try {
			// 3������ͬ���
			String zipfile = service.zipPdf(project_id);
			// 4���ҽӵ���Ŀ����
			service.updateProjectPdfInfo(project_id, zipfile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ActionException("��ͬ�����쳣������ϵ����Ա��");
		}
		return new ReplyAjax(true, "���ɺ�ͬ�ɹ�");
	}

/**
	 * @param uuid2 
	 * 
	 * @Title: producePdf 
	 * @Description: (PDFģ�嵼������) 
	 * @param pdfTemplateName	ģ��  file��name like 
	 * @param acrobatContext	ģ��sql�ж�Ӧ�Ĳ���
	 * @throws IOException
	 * @throws DocumentException 
	 * @return String 
	 * @throws 
	 */
	public String producePdf_new(String tpm_code,String pdfTemplateName,String pdfType,Map<String,Object> acrobatContext, String uuid2) throws IOException, DocumentException{
		String paramStr = acrobatContext.toString();//����sql�еı���  ��ҪΪ�� PROJECT_ID
		String uuid = StringUtils.nullToOther(uuid2, generateShortUuid());//��ˮ��
		
		String inputFilePath = "";
		File fi = new File((String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+uuid);
		if(!fi.exists()) fi.mkdirs();
		String outputFile = (String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+uuid+"/"+tpm_code+"-"+pdfTemplateName.replaceAll(".pdf", "")+".pdf";//  ���ص���ʱ�ļ�
		String key = "";
		String TEMPLATE_FILE_ID = "";

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("TPM_CODE", tpm_code);
		param.put("PDFTEMPLATENAME", pdfTemplateName);
		param.put("PDF_TYPE", pdfType);
		
		Map<String,Object> template = Dao.selectOne("SRV_AIF.getTmpIdByName", param);
		if(template == null || template.size() == 0 ){
			throw new ActionException("δ�ҵ���Ӧģ�壬��������{"+tpm_code+","+pdfTemplateName+","+pdfType+"}");
		}
		TEMPLATE_FILE_ID = StringUtils.nullToString(template.get("ID"));//�ļ�ID
		key = StringUtils.nullToString(template.get("TPF_TPM_ID"));//ģ��ID
		inputFilePath = StringUtils.nullToString(template.get("PDF_PATH"));//�ļ�·��
		String is_multi=StringUtils.nullToString(template.get("IS_MULTI"));
		String SQL=StringUtils.nullToString(template.get("SQL"));
		String SQL_FIELD=StringUtils.nullToString(template.get("SQL_FIELD"));
		
		//ģ��Ψһ��ʶ��ע��
		acrobatContext.put("UUID", uuid);
		acrobatContext.put("��ͬ��ӡ��ˮ��", uuid);
		acrobatContext.put("��ͬ��ˮ��", uuid);
		
		if (StringUtils.isNotBlank(SQL) && StringUtils.isNotBlank(SQL_FIELD)) {
//			���SQL��ֵ�������SQL��ֵ�ж�����0����ݺ�ͬ
			List<HashMap<String,Object>> listFile = executeSql(SQL,acrobatContext);
			String[] lstSqlKeys = SQL_FIELD.trim().toUpperCase().split(",");
			for (HashMap<String,Object> fillData : listFile) {
//				����������ѭ��ȡֵ
				for(String SqlKey :lstSqlKeys){
					if (fillData != null && !fillData.isEmpty() && fillData.containsKey(SqlKey)) {
						acrobatContext.put(SqlKey, fillData.get(SqlKey));
					}
				}
				//ȡ��һ������ֵ��Ϊ�ļ�����׺
				if("1".equals(is_multi)){
					outputFile=(String) SkyEye.getConfig().get("file.path.temp")+"/pdfTemplate/hist/"+uuid+"/"+tpm_code+"-"+pdfTemplateName.replaceAll(".pdf", "")+"_"+fillData.get(lstSqlKeys[0])+".pdf";
				}
				//======================================================
				//ע������ TODO
				getAcrobatContextNew(key,acrobatContext);
				logger.info("-----------"+acrobatContext);
				
//				���ݲ���ֵ����PDF
				fillFields(inputFilePath, acrobatContext, outputFile);		
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("TEMPLATE_FILE_NAME", pdfTemplateName);
				params.put("TEMPLATE_FILE_ID", TEMPLATE_FILE_ID);
				params.put("EXPORT_FILE_PATH", outputFile);
				params.put("PARAMS", paramStr);
				params.put("UUID", uuid);
				params.put("TEMPLATE_MAIN_ID", key);
				params.put("PROJECT_ID", acrobatContext.get("PROJECT_ID"));
				params.put("USER_NAME", Security.getUser()!=null ? Security.getUser().getName():"δ��¼");
				Dao.insert("SRV_AIF.insertPdfExpHist", params);
				
			}
		}else{
//			���SQL û��ֵ���ʾ��������һ�ݺ�ͬ
			//ע������ TODO
			getAcrobatContextNew(key,acrobatContext);
			logger.info("-----------"+acrobatContext);
			
//			���ݲ���ֵ����PDF
			fillFields(inputFilePath, acrobatContext, outputFile);		
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("TEMPLATE_FILE_NAME", pdfTemplateName);
			params.put("TEMPLATE_FILE_ID", TEMPLATE_FILE_ID);
			params.put("EXPORT_FILE_PATH", outputFile);
			params.put("PARAMS", paramStr);
			params.put("UUID", uuid);
			params.put("TEMPLATE_MAIN_ID", key);
			params.put("PROJECT_ID", acrobatContext.get("PROJECT_ID"));
			params.put("USER_NAME", Security.getUser()!=null ? Security.getUser().getName():"δ��¼");
			Dao.insert("SRV_AIF.insertPdfExpHist", params);
			
		}
		
		
		
		
			
			
		
		acrobatContext.put("PROJECT_ID",  acrobatContext.get("PROJECT_ID"));
		Dao.update("SRV_AIF.updateProjectPdfStatusInfo", acrobatContext);
		
		return outputFile;
	}



/**
	 * 
	* ������ͬ��zip��
	 * @param name_paths 
	 * @throws IOException 
	* @date 2017��10��10�� ����1:51:47 
	 */
	public String zipPdfSeries(List<Map<String, Object>> pdfs, String seriesId) throws IOException  {
		String basePath = (String) SkyEye.getConfig().get("file.path.temp") + "/pdfTemplate/ExportZipPdfSeries/";
		//����ѹ����pdfģ���ļ���·��
		String filePath = basePath + "series" + seriesId;
		
		//��������Դ�ļ�����
		File file = new File(filePath);
		if (file.exists()) ZipUtil.deleteDir(file);
		file.deleteOnExit();
		file.mkdirs();
		for (Map<String, Object> name_path : pdfs) {
			//pdfģ���ļ���
			String pdfName = StringUtils.nullToString(name_path.get("NAME"));
			//pdfģ���ļ�·��
			String pdfPath = StringUtils.nullToString(name_path.get("PDF_PATH"));
			//pdfģ������ѹ��Դ·��
			String newFilePath = filePath+"/"+pdfName.replaceAll(".pdf", "")+".pdf";
			file = new File(pdfPath);
			//���������洢��pdfģ�忽����pdfģ������ѹ��Դ·����
			try {
				FileUtils.copyFile(file, new File(newFilePath));
			} catch (IOException e) {
				e.printStackTrace();
				throw new ActionException("����Ϊ��"+name_path.get("TPM_CODE") +"��Ϊ:"+pdfName+"��ͬģ�岻���ڣ����ʵ��ģ��·���Ƿ���ȷ��");
			}
		}
		//���ѹ����·��
		String zipfile = filePath+".zip";
		ZipUtil.zip(filePath, zipfile, "GBK");
		return zipfile;
	}