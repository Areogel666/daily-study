package cn.lxr.app.springbootDemo;

/***
 * 本地测试 MQ队列提供者
 */
@RestController
@RequestMapping("/")
public class MainController {

    //注入存放消息的队列
    @Autowired
    private Queue queueI;

    //注入存放消息的队列
    @Autowired
    private Queue queueD;

    //注入springboot封装的工具类
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    //报表立即生成队列发送
    //适用于GET请求
    @GetMapping("sendI")
    public void sendI(@RequestParam(value="id", required = false)Integer id, String name) {
    	System.out.println("id="+id);
        jmsMessagingTemplate.convertAndSend(queueI, name);
    }
    
    //适用于GET请求
    @RequestMapping("sendI")
    public void sendI(Integer id, String name) {
        jmsMessagingTemplate.convertAndSend(queueI, name);
    }
    
    //适用于POST请求
    @RequestMapping(value="sendI",method=RequestMethod.POST)
    public String sendI(@RequestBody ReportModel report) {
    	jmsMessagingTemplate.convertAndSend(queueI, report);
    	String msg = jmsMessagingTemplate.convertSendAndReceive(queueI, String.class);
    	return msg;
    }

    //适用于POST或GET请求
    @RequestMapping("sendI")
    public String sendI(HttpServletRequest request) {
    	String name = request.getParameter("name");
    	jmsMessagingTemplate.convertAndSend(queueI, name);
    	String msg = jmsMessagingTemplate.convertSendAndReceive(queueI, String.class);
    	return msg;
    }
    
    //适用于POST或GET请求
    @RequestMapping("sendI")
    public String sendI(ReportModel report) {
    	jmsMessagingTemplate.convertAndSend(queueI, report);
    	String msg = jmsMessagingTemplate.convertSendAndReceive(queueI, String.class);
    	return msg;
    }

}
