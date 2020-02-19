package com.rh.cloudcampus.edu.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * 图片处理工具类
 * @author Rex
 */
public class ImageUtil{
	
    /**
     * 本地图片转byte数组
     * @param path
     * @return
     */
    public static byte[] image2byte(String path){
	      byte[] data = null;
	      FileImageInputStream input = null;
	      try {
		        input = new FileImageInputStream(new File(path));
		        ByteArrayOutputStream output = new ByteArrayOutputStream();
		        byte[] buf = new byte[1024];
		        int numBytesRead = 0;
		        while ((numBytesRead = input.read(buf)) != -1) {
		        	output.write(buf, 0, numBytesRead);
		        }
		        data = output.toByteArray();
		        output.close();
		        input.close();
	      }
	      catch (FileNotFoundException ex1) {
	    	  ex1.printStackTrace();
	      }catch (IOException ex1) {
	    	  ex1.printStackTrace();
	      }
	      return data;
    }
    
    /**
     * 图片格式转换
     * @param path 图片路径  "/Users/zhangjing/Desktop/项目相关资料/JWT.png"
     * @param toPath  转出路径  "/Users/zhangjing/Desktop/base64.jpg"
     * @return
     */
    public static boolean imageFormat(String path,String toPath){
	 	String s = Base64.encodeBase64String(ImageUtil.image2byte(path));
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(s);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}

			OutputStream out = new FileOutputStream(toPath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    /**
	 * 在线图片转换成base64字符串
	 * @param imgURL	图片线上路径
	 * @return
	 */
	public static String ImageToBase64ByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.encodeBase64String(data.toByteArray());
	}
	
	/**
	 * 在线图片转换成二进制数组
	 * @param imgURL	图片线上路径
	 * @return
	 */
	public static byte[] ImageToByteByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data.toByteArray();
	}

	/**
	 * base64字符串转换成本地图片
	 * @param imgStr		base64字符串
	 * @param imgFilePath	图片本地存放路径
	 * @return
	 */
	public static boolean Base64ToImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片

		try {
			byte[] b = Base64.decodeBase64(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
 
	}
 
	/**
     * 图片压缩
     * @param srcImage  源图片文件路径        （如：srcImage="G:/32/2015101713.jpg"）
     * @param tarImage  目的图片文件路径    （如：tarImage="G:/32/2015101713_720_720.jpg"）
     * @param maxPixel  转换的像素                 （如：maxPixel=720）
     * @param
     */
    public static boolean transformer(String srcImage,String tarImage,int maxPixel) {
        //源图片文件
        File srcImageFile = new File(srcImage);
        //目的图片文件
        File tarImageFile = new File(tarImage);
        // 生成图片转化对象
        AffineTransform transform = new AffineTransform();
        // 通过缓存读入缓存对象
        BufferedImage image = null;
        try {
            image = ImageIO.read(srcImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int imageWidth = image.getWidth();//原图片的高度
        int imageHeight = image.getHeight();//原图片的宽度
        int changeWidth = 0;//压缩后图片的高度
        int changeHeight = 0;//压缩后图片的宽度
        double scale = 0;// 定义小图片和原图片比例
        if (maxPixel != 0) {
            if (imageWidth > imageHeight) {
                changeWidth = maxPixel;
                scale = (double) changeWidth / (double) imageWidth;
                changeHeight = (int) (imageHeight * scale);
            } else {
                changeHeight = maxPixel;
                scale = (double) changeHeight / (double) imageHeight;
                changeWidth = (int) (imageWidth * scale);
            }
        } 
        // 生成转换比例
        transform.setToScale(scale, scale);
        // 生成转换操作对象
        AffineTransformOp transOp = new AffineTransformOp(transform, null);
        //生成压缩图片缓冲对象
        BufferedImage basll = new BufferedImage(changeWidth, changeHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        //生成缩小图片
        transOp.filter(image, basll);
        try {
            //输出缩小图片
            ImageIO.write(basll, "jpeg",tarImageFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
	 * 将图片压缩到指定大小以内
	 * @param srcImgData 源图片数据
	 * @param maxSize 目的图片大小
	 * @return 压缩后的图片数据
	 */
	public static byte[] compressUnderSize(byte[] srcImgData, long maxSize) {
		double scale = 1;
		byte[] imgData = Arrays.copyOf(srcImgData, srcImgData.length);
 
		if (imgData.length > maxSize) {
			do {
				try {
					imgData = compress(imgData, scale,"jpg");
 
				} catch (IOException e) {
					throw new IllegalStateException("压缩图片过程中出错，请及时联系管理员！", e);
				}
 
			} while (imgData.length > maxSize);
		}
 
		return imgData;
	}
	
	/**
	 * 按照 宽高 比例压缩
	 * @param imgIs 待压缩图片输入流
	 * @param scale 压缩刻度
	 * @param out 输出
	 * @return 压缩后图片数据
	 * @throws IOException 压缩图片过程中出错
	 */
	public static byte[] compress(byte[] srcImgData, double scale,String type) throws IOException {
		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
		int width = (int) (bi.getWidth() * scale); // 源图宽度
		int height = (int) (bi.getHeight() * scale); // 源图高度
 
		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
 
		Graphics g = tag.getGraphics();
		g.setColor(Color.RED);
		g.drawImage(image, 0, 0, null); // 绘制处理后的图
		g.dispose();
 
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ImageIO.write(tag, type, bOut);
 
		return bOut.toByteArray();
	}


	 /**
     * 将二进制转换为本地图片
     * 
     * @param base64String
     */ 
    static boolean base64StringToImage(byte[] bytes1,String url) { 
        try { 
   
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1); 
            BufferedImage bi1 = ImageIO.read(bais); 
            File w2 = new File(url);// 可以是jpg,png,gif格式 
            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动 
            return true;
        } catch (IOException e) { 
            e.printStackTrace(); 
            return false;
        } 
    } 
    
	public static void main(String[] args) {
//		//本地图片转byte数组
//		byte [] b = image2byte("/Users/zhangjing/Desktop/test.jpg");
//		System.out.println(b);
//		
//		// 图片格式转换
//		boolean s = imageFormat("/Users/zhangjing/Desktop/test.jpg","/Users/zhangjing/Desktop/test_imageFormat.png");
//		System.out.println(s);
//		
//		/在线图片转换成base64字符串
//		String base64Str = ImageToBase64ByOnline("http://p20xj81jp.bkt.clouddn.com/Fh4kCTG7yCV5KtysLKhQ5xIbpzsb");
//		/System.out.println(base64Str);
//		
//		//在线图片转换成二进制数组
//		byte[] itbb = ImageToByteByOnline("http://p20xj81jp.bkt.clouddn.com/Fh4kCTG7yCV5KtysLKhQ5xIbpzsb");
//		System.out.println(itbb);
//		
//		//base64字符串转换成图片
//		boolean d = Base64ToImage(base64Str,"/Users/zhangjing/Desktop/base64Str_Base64ToImage.jpg");
//		System.out.println(d);
//		
//		//图片像素压缩
//		boolean t = transformer("/Users/zhangjing/Desktop/test.jpg","/Users/zhangjing/Desktop/transformer_test.jpg",720);
//		System.out.println(t);
		
		
		byte []  c = compressUnderSize(image2byte("/Users/zhangjing/Downloads/banner-01.png"),100*1024);
		boolean result = base64StringToImage(c,"/Users/zhangjing/Downloads/compressUnderSize_banner-08.png");
		System.out.println(result);
	}

}
