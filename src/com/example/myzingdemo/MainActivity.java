package com.example.myzingdemo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 可以使用 验证通过
 * @ClassName: MainActivity 
 * @Description: TODO 
 * @date 2016-4-26 下午3:21:11 
 *
 */

public class MainActivity extends Activity implements OnClickListener,OnLongClickListener{

	private Button btnCrtCode;
	private ImageView imgCode;
	private EditText etCodeContent;
	private TextView tvTag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initdata();
	}
	/**
	 * 
	 */
	private void initdata(){
		btnCrtCode = (Button) findViewById(R.id.btn_create_two_code);
		btnCrtCode.setOnClickListener(this);
		imgCode = (ImageView) findViewById(R.id.img_create_tow_code);
		imgCode.setOnLongClickListener(this);
		etCodeContent = (EditText) findViewById(R.id.et_tow_code);
		tvTag = (TextView) findViewById(R.id.tv_two_code_tag);
	}
	
	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.img_create_tow_code://长按二维码 显示内容
			Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			Bitmap bitmap = ((BitmapDrawable)imgCode.getDrawable()).getBitmap();
			RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
//	            LuminanceSource source = new RGBLuminanceSource(path);  
	            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));  
	             QRCodeReader reader2= new QRCodeReader();
	             Result result;
	           try {
	             result = reader2.decode(bitmap1,hints);
	             System.out.println("res"+result.getText());
	             tvTag.setText(result.getText());
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ChecksumException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;

		default:
			break;
		}
		return false;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_create_two_code://生成二维码
			QRCodeReader reader = new QRCodeReader(); 
	        int width = 200, height = 200;  
	        QRCodeWriter writer = new QRCodeWriter();
	        try {
	        	//edit.getText();
//	        	Log.i(TAG, "编辑框中的内容： " + edit.getText().toString());
//	        	System.out.println(edit.getText().toString());
	        	if(etCodeContent.getText().toString().length()<1)
	        	{
	        		return;
	        	}
	        	
	        	BitMatrix martix = writer.encode(etCodeContent.getText().toString(), BarcodeFormat.QR_CODE, width, height);
	        	System.out.println("w:"+martix.getWidth()+"h:"+martix.getHeight()); 
	        	String imageFormat = "png"; 
	        	Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
	        	hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	        BitMatrix bitMatrix = new QRCodeWriter().encode(etCodeContent.getText().toString(),BarcodeFormat.QR_CODE, width, height,hints);
	        	 int[] pixels = new int[width * height];    
	             for (int y = 0; y < height; y++) {    
	                 for (int x = 0; x < width; x++) {    
	                     if(bitMatrix.get(x, y)){    
	                         pixels[y * width + x] = 0xff000000;    
	                     }else{
	                    	 pixels[y * width + x] = 0xffffffff;
	                     }    
	                         
	                 }    
	             }    
	                 
	             Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);    
	                
	             bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	             imgCode.setImageBitmap(bitmap);
//	             RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
//	            LuminanceSource source = new RGBLuminanceSource(path);  
//	            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));  
//	             QRCodeReader reader2= new QRCodeReader();
//	             Result result = reader2.decode(bitmap1);
//	             System.out.println("res"+result.getText());
	             
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		
	}

}
