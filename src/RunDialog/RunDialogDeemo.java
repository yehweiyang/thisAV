package RunDialog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpURLConnection 模擬流覽器http請求處理測試
 * 
 * 整體操作流程(注意順序) 1.根據url位址創建URL物件 2.url.openConnection() 得到HttpURLConnection
 * 3.設置HttpURLConnection的配置(會根據配置生成http請求頭資訊) 4.httpConn.connect()建立與伺服器的TCP連接
 * 5.通過httpConn得到輸出流,並寫入參數
 * 6.通過httpConn得到輸入流,並讀取伺服器返回資訊(這時才真正產生http請求發送給伺服器,但可以根據配置來改變;一般返回可能是html字串)
 * 
 * httpRequestPost/httpRequestGet兩種方式測試通過，返回的是html字串(後臺是jsp)
 * 
 */
public class RunDialogDeemo {
	String lines1;
	String lines2;

	public static void getPageData(String url) throws Exception {
		RunDialogDeemo t = new RunDialogDeemo();
		t.httpUrlConnection(url);
	}

	private void httpUrlConnection(String urlStr) throws Exception {
		httpRequestGet(urlStr);
	}

	/**
	 * http get 請求
	 * 
	 * @param urlStr
	 *            請求URL位址
	 * @throws Exception
	 */
	private void httpRequestGet(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		// openConnection函數會根據URL的協定返回不同的URLConnection子類的物件
		// 這裡URL是一個http,因此實際返回的是HttpURLConnection
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestProperty("Content-Type", "UTF-8");
		httpConn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		// 進行連接,實際上request要在下一句的connection.getInputStream()函數中才會真正發到 伺服器****待驗證
		httpConn.connect();

		// 取得輸入流，並使用Reader讀取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpConn.getInputStream(), "UTF-8"));

		System.out.println("=========get request接收資料內容開始============");
		String lines;
		while ((lines = reader.readLine()) != null) {

			if (lines.indexOf("flv") != -1) {
				// 取得網址
				int a = lines.indexOf("http");
				int b = lines.indexOf("flv") + 3;
				lines1 = lines.substring(a, b);
				System.out.println(lines.substring(a, b));
			}
			if (lines.indexOf("<h1>") != -1) {
				// 取得檔案名稱
				int a = lines.indexOf("<h1>") + 4;
				int b = lines.indexOf("</h1>");
				lines2 = lines.substring(a, b);
				System.out.println(lines.substring(a, b));
			}

		}
		// ---------------------------
		// -******

		try {
			URL zeroFile = new URL(lines1);
			BufferedInputStream bs = new BufferedInputStream(
					zeroFile.openStream());
			byte[] b = new byte[802400];// 一次取得 8024*100 個 bytes
			FileOutputStream fs = new FileOutputStream("d:\\" + lines2 + ".flv");
			int len;
			while ((len = bs.read(b, 0, b.length)) != -1) {

				fs.write(b, 0, len);
			}
			// System.out.println("done====>");
			bs.close();
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("讀取檔案寫入時發生錯誤");
		}

		reader.close();
		System.out.println("=========get request接收資料內容結束============");
		httpConn.disconnect();
	}

}
