package com.ljt.ds.admin.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ljt.ds.admin.service.TestService;


@Controller
@EnableAutoConfiguration
public class SampleController {
	@Autowired
	private TestService ser;
	
    @RequestMapping("/home")
    String home(Model model) {
    	//model.addAttribute("userName", "李文科");
    	//List<Map<String, Object>> findAll = ser.findAll();
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("flg_a", "0");
    	list.add(map);
    	model.addAttribute("list", list);
        return "index";
    }
    
    @RequestMapping("/wxpay/notify")
    String wxPay(Model model,HttpServletRequest request){
    	System.out.println("================微信异步通知=================");
 
    	/*ServletInputStream in;
		try {
			in = request.getInputStream();
			BufferedReader reader =new BufferedReader(new InputStreamReader(in));  
	        String sReqData="";  
	        String itemStr="";//作为输出字符串的临时串，用于判断是否读取完毕  
	        while(null!=(itemStr=reader.readLine())){  
	            sReqData+=itemStr;  
	        }  
	        //----  待修改，可以在这里写一个log日志文件，记录相应信息  
	        System.out.println(sReqData);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */ 
		
		byte[] buffer = new byte[64*1024];
		InputStream in;
		try {
			in = request.getInputStream();
			int length = in.read(buffer);
			String encode = request.getCharacterEncoding();

			byte[] data = new byte[length];
			System.arraycopy(buffer, 0, data, 0, length);
			String context = new String(data, encode);
			System.out.println(context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return "";
    }
}
