package cn.lxr.util.baseUtil;
        //用http方式发送Json报文
        public JSONObject sendHttpRequest(){   
            String resp= null;
            JSONObject obj = new JSONObject();
            obj.put("name", "张三");   
            obj.put("age", "18");   
            String query = obj.toString();
            log.info("发送到URL的报文为：");
            log.info(query);
            try {
                URL url = new URL("http://127.0.0.1:8888"); //url地址

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("Content-Type","application/json");
                connection.connect();

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(query.getBytes("UTF-8"));
                }

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    String lines;
                    StringBuffer sbf = new StringBuffer();
                    while ((lines = reader.readLine()) != null) {
                        lines = new String(lines.getBytes(), "utf-8");
                        sbf.append(lines);
                    }
                    log.info("返回来的报文："+sbf.toString());
                    resp = sbf.toString();    
                   
                }
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                JSONObject json = (JSONObject)JSON.parse(resp);
            }
        }
