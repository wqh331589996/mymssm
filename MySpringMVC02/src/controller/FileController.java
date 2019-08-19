package controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/** 文件上传控制器 */
@Controller
public class FileController {

	@RequestMapping("index.html")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/upload.html", method = RequestMethod.POST)
	public String upload(@RequestParam("desc") String desc, HttpServletRequest request,
			@RequestParam(value = "myfile", required = false) MultipartFile myfile) {

		String myFilePath = null;// 文件保存到数据库的路径

		System.out.println("文件描述:" + desc);

		if (!myfile.isEmpty()) {// 图片有上传
			// 读取uploads的真实路径
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploads");
			System.out.println("path:" + path);
			// 读取原文件名
			String oldFileName = myfile.getOriginalFilename();
			System.out.println("原文件名:" + oldFileName);
			// 读取文件扩展名
			String extName = FilenameUtils.getExtension(oldFileName);
			System.out.println("扩展名:" + extName);
			int fileSize = 5000000;// 文件大小5M
			// 读取文件大小
			System.out.println("文件大小:" + myfile.getSize());

			if (myfile.getSize() > fileSize) {// 文件超过限定
				request.setAttribute("uploadFileError", "*上传文件超过5M!");
				return "error";
			} else if (extName.equalsIgnoreCase("jpg") || extName.equalsIgnoreCase("png")
					|| extName.equalsIgnoreCase("jpeg") || extName.equalsIgnoreCase("pneg")) {// 扩展名是否合法
				// 重命名文件
				String fileName = System.currentTimeMillis() + "_user." + extName;
				System.out.println("新文件名:" + fileName);
				// 创建文件对象,接收从客户端已经传到服务器的文件流
				File targetFile = new File(path, fileName);

				try {
					myfile.transferTo(targetFile); // 保存文件
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//文件路径
				myFilePath="statics" + File.separator + "uploads"+File.separator+fileName;
				System.out.println("保存到数据库的文件路径:"+myFilePath);
			} else {
				request.setAttribute("uploadFileError", "*上传文件格式不正确!");
				return "error";
			}

		}

		return "success";
	}

}
