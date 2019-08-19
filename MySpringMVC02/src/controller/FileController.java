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

/** �ļ��ϴ������� */
@Controller
public class FileController {

	@RequestMapping("index.html")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/upload.html", method = RequestMethod.POST)
	public String upload(@RequestParam("desc") String desc, HttpServletRequest request,
			@RequestParam(value = "myfile", required = false) MultipartFile myfile) {

		String myFilePath = null;// �ļ����浽���ݿ��·��

		System.out.println("�ļ�����:" + desc);

		if (!myfile.isEmpty()) {// ͼƬ���ϴ�
			// ��ȡuploads����ʵ·��
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploads");
			System.out.println("path:" + path);
			// ��ȡԭ�ļ���
			String oldFileName = myfile.getOriginalFilename();
			System.out.println("ԭ�ļ���:" + oldFileName);
			// ��ȡ�ļ���չ��
			String extName = FilenameUtils.getExtension(oldFileName);
			System.out.println("��չ��:" + extName);
			int fileSize = 5000000;// �ļ���С5M
			// ��ȡ�ļ���С
			System.out.println("�ļ���С:" + myfile.getSize());

			if (myfile.getSize() > fileSize) {// �ļ������޶�
				request.setAttribute("uploadFileError", "*�ϴ��ļ�����5M!");
				return "error";
			} else if (extName.equalsIgnoreCase("jpg") || extName.equalsIgnoreCase("png")
					|| extName.equalsIgnoreCase("jpeg") || extName.equalsIgnoreCase("pneg")) {// ��չ���Ƿ�Ϸ�
				// �������ļ�
				String fileName = System.currentTimeMillis() + "_user." + extName;
				System.out.println("���ļ���:" + fileName);
				// �����ļ�����,���մӿͻ����Ѿ��������������ļ���
				File targetFile = new File(path, fileName);

				try {
					myfile.transferTo(targetFile); // �����ļ�
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//�ļ�·��
				myFilePath="statics" + File.separator + "uploads"+File.separator+fileName;
				System.out.println("���浽���ݿ���ļ�·��:"+myFilePath);
			} else {
				request.setAttribute("uploadFileError", "*�ϴ��ļ���ʽ����ȷ!");
				return "error";
			}

		}

		return "success";
	}

}
