import java.io.*;
import java.util.*;
import java.text.*;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.sql.*;

//@WebServlet(name = "users", urlPatterns = {"/users"}, loadOnStartup=1)
@WebServlet(name = "schedul", urlPatterns = {"/schedul"}, loadOnStartup=1)
public class FCTMServlet extends HttpServlet 
{
    public static final String TABLE_MEMBER = "member";
    public static final String TABLE_ONTHEFLY = "onthefly";
    public static final String TABLE_SCHEDUAL = "schdul";   // SCHDUL
    public static final String TABLE_HEALTH = "health";   //health
    public static final String TABLE_GPS = "gps";   //GPS
    // 儲存客戶端傳過來的網頁參數
    class Action
    {
        public static final String REGISTER = "register";
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
        public static final String INSERT = "insert";
        public static final String QUERY = "query";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";
        public static final String CLEAR = "clear";
        public static final String COUNT = "count";
        public static final String LINK = "link";

        public static final String TYPE_MEMBER = "member";
        public static final String TYPE_ONTHFLY = "onthefly";
        public static final String TYPE_EMPLOYER = "employer";
        public static final String TYPE_NURSE = "nurse";
        public static final String TYPE_LINK = "link";
        public static final String TYPE_UNLINK = "unlink";
        
        public static final String TYPE_SCHEDUAL = "schdul";
        public static final String TYPE_HEALTH = "health";
        public static final String TYPE_GPS = "gps";

        public Action(String name, String type, String data1, String data2)
        {
            this.name = name;
            this.type = type;
            this.data1 = data1;
            this.data2 = data2;
        }

        public String name;
        public String type;
        public String data1;
        public String data2;
    }

    private static final long serialVersionUID = 1L;
       

    PrintWriter out = null;

   
    HttpSession session = null;
    
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {   

      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=UTF-8");

        out = response.getWriter();

        // 判斷有沒有給Action
        if(request.getParameter("action") == null)
        {
            showUsage();
            return;
        }

        session = request.getSession();
        
     
        session.setMaxInactiveInterval(3600);
        
        
     
        Action action = new Action(request.getParameter("action"),
                                   request.getParameter("type"),
                                   request.getParameter("data1"), 
                                   request.getParameter("data2"));

       
        showAction(action);


        doAction(action, request);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    }

    private void showUsage()
    {
        out.println("<h2>Action使用說明：</h2>");
        out.println("echo - 將傳給伺服器的參數顯示在網頁上（extraString=無，extraInt=無）<br/>");
        out.println("saveFile - 將一筆資料存到伺服器的檔案當中。extraString=要寫入檔案的字串，extraInt=要寫入檔案的整數。<br/>");
        out.println("readFile - 讀取檔案資料。（extraString=無，extraInt=無）<br/>");
        out.println("DB - 插入一筆資料到資料庫（extraString=字串資料，extraInt=整數資料）<br/>");
        out.println("updateDB - 更新一筆資料庫中資料（extraString=要更新的資料，extraInt=新的資料值）<br/>");
        out.println("readDB - 讀取一筆資料庫資料（extraString=要讀取的資料，extraInt=無）<br/>");
        out.println("deleteDB - 刪除資料庫資料（extraString=要刪除的資料名稱，extraInt=無）<br/>");
        out.println("login - 模擬登入（extraString=uch, extraInt=12345678）<br/>");
        out.println("logout - 模擬登出（extraString=無，extraInt=無）<br/>");
        out.println("<h3>範例：</h3>");
        out.println("<ul>");

       out.println("<li>http://52.11.214.88:8080/4/schedul?action=&type=schedual&data1=test&data2=test&data3=test&data4=test</li>");
        out.println("<li>http://52.11.214.88:8080/4/schedul?action=updateDB&type=member&data1=test</li>");
        out.println("<li>http://52.11.214.88:8080/4/schedul?action=query&type=schedual&data1=test</li>");
        out.println("<li>http://52.11.214.88:8080/4/schedul?action=updateDB&type=schedual&data1=1234</li>");
        out.println("<li>http://52.11.214.88:8080/4/schedul?action=delete&type=schedual&data1=1234</li>");
        out.println("<li>http://52.11.214.88:8080/4/schedul?action=query&type=schedual&data1=1234</li>");
        out.println("<li>http://52.11.214.88:8080/4/schedul?action=insert&type=schedual&data1=test2&data2=%E5%9B%BA%E5%AE%9A%E8%A1%8C%E7%A8%8B`5%E6%99%8200%E5%88%86`6%E6%99%8200%E5%88%86`%E6%8C%89%E6%91%A9</li>");
          out.println("</ul><br/>");
        out.println("備註：每個Action輸出最後一行為執行結果，適合應用在應用程式當中，可由最後一行輸出結果來判斷成功與否或得到回傳值。");
    }

    private void showAction(Action action)
    {
        out.println("<h2>網頁傳入資料：</h2>");
        out.println("action=" + action.name + "<br/>");
        out.println("type=" + action.type + "<br/>");
        out.println("data1=" + action.data1 + "<br/>");
        out.println("data2=" + action.data2 + "<br/>");
        out.println("<br/>");
        out.println("<h2>伺服器執行結果：</h2>");
    }
    
    private boolean doAction(Action action, HttpServletRequest request) throws ServletException, IOException, IllegalStateException
    {
        if(action != null)
        {
            switch(action.name)
            {
            case Action.LOGOUT:
                // 登出後需移除使用者資訊
                session.removeAttribute("username");
                out.println("true");
                break;

            case Action.LOGIN:
            case Action.INSERT:
            case Action.QUERY:
            case Action.UPDATE:
            case Action.DELETE:
            case Action.COUNT:
            case Action.LINK:
                // 操作資料庫，傳入資料庫檔案路徑
           	runDB(action, request.getSession().getServletContext().getRealPath("/WEB-INF/Schedul.db"));     
                break;

            default:
                out.println("noaction");
            }
        }
        
        return true;
    }

    // 登入
    private String login(Statement statement, Action action) throws SQLException
    {
        ResultSet rs = statement.executeQuery("select * from " + TABLE_MEMBER + " where NUMBER='" + action.data1 + "'");
        
        if(rs.next()) // ResultSet有資料
        {
            String password = rs.getString(6);
            String status = rs.getString(15);
            
            // 判斷帳號和密碼是否相同
            if(password.equals(action.data2) && status.equals("normal")) 
            {
                // 登入成功則將使用者資訊寫入Session，才能讓每個頁面都取得登入資訊
                session.setAttribute("username", action.data1);
                return "true";
            }
            else
                out.println("密碼錯誤：" + action.data2 + "<br/>");
        }
        else 
        {
            out.println("無此帳號：" + action.data1 + "<br/>");
            
            // 登入失敗則移除Session的使用者資訊
            session.removeAttribute("username");
        }

        return "false";
    }

    // 資料庫共通方法
    private void runDB(Action action, String dbPath)
    {
        out.println("資料庫位置：" + dbPath + "<br/>");
        
        // 存放執行結果
        String result = "false";
        
        // 步驟一: import java.sql.*;   
        Connection connect = null;
        Statement statement = null;
        try
        {
            // 步驟二: 註冊JDBC驅動程式
            Class.forName("org.sqlite.JDBC");
            
            // 步驟三: 建立資料庫連線(如果資料庫不存在, 將直接建立資料庫)
            connect = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            
            // 步驟四: 透過Connection物件建立Statement物件
            statement = connect.createStatement();
            
            // 步驟五: 透過Statement物件執行SQL敘述
            statement.executeUpdate("create table if not exists member(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                                       "NUMBER    TEXT," +
                                                                       "PASSWORD  TEXT," +
                                                                       "EMAIL     TEXT," +
                                                                       "PHONE     TEXT," +
																	  																	 "LAB_NUMBER    TEXT," +
																	  																	 "LAB_PASSWORD     TEXT," +
                                                                       "REG_DATE  TEXT," +
                                                                       "STATUS    TEXT," +
                                                                       "RESERVED  TEXT)");
            //Ann 新增排程
           
            if(action.type.equals(Action.TYPE_SCHEDUAL)){
            statement.executeUpdate("create table if not exists schdul(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "SCH         TEXT," +
                    "TIMEBEG     TEXT," +
                    "TIMEEND     TEXT," +
                    "WORKITEM    TEXT," +
                    "STATUS      TEXT," + 
                    "MEMBERID    TEXT)");
            }
            //Ann 新增健康記錄
            else if(action.type.equals(Action.TYPE_HEALTH)){
            statement.executeUpdate("create table if not exists health(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "blood_glucose   TEXT," +
                    "blood_pressure  TEXT," +
                    "palpitant     	 TEXT," +
                    "pulse     			TEXT," +    
                    "datetime   		 TEXT," +    
                    "status    				TEXT," +              
                    "MEMBERID  				TEXT)");
            }else if(action.type.equals(Action.TYPE_GPS)){          	
             //Ann GPS 外傭GPS
            statement.executeUpdate("create table if not exists GPS(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Latitude     TEXT," +
                    "Longitude    TEXT,"+
                    "status    TEXT," + 
                    "MEMBERID    TEXT)");
            }
            else{
            	
            }

            // 插入資料
            switch(action.name)
            {
            case Action.LOGIN:  result = login(statement, action);   break;
            case Action.INSERT: result = insertDB(statement, action); break;
            case Action.QUERY:  result = queryDB(statement, action);  break;
            case Action.UPDATE: result = updateDB(statement, action); break;
            case Action.DELETE: result = deleteDB(statement, action); break;
            case Action.COUNT:  result = countDB(statement, action);  break;
            case Action.LINK:   result = linkDB(statement, action);   break;
            default: 
                new SQLException("錯誤的Action");
            }
        }
        catch(ClassNotFoundException e)
        {
            out.println("錯誤：註冊JDBC失敗。<br/>");
        }
        catch(SQLException e)
        {
            out.println("錯誤：" + e.toString() + "<br/>");
        }
        finally
        {
            try
            {
                // 步驟六: 關閉資料庫連線(Statement和Connection)
                if(statement != null) statement.close();
                if(connect != null) connect.close();
                
                // 回傳執行結果
                out.println(result);
                return;
            }
            catch(SQLException e)
            {
                out.println("錯誤：資料庫關閉錯誤。<br/>");
            }
        }

        out.println("false");
    }

    // 新增一筆會員資料或看護資料
    private String insertDB(Statement statement, Action action) throws SQLException
    {
        out.println("0.insertDB()<br/>"+action.type);

        // 新增會員資料
        if(action.type.equals(Action.TYPE_MEMBER))
        {
            String [] datas = action.data2.split("`");

            String sqlCommand = "insert into " + TABLE_MEMBER + "(NUMBER,PASSWORD,EMAIL,PHONE,LAB_NUMBER,LAB_PASSWORD,REG_DATE,STATUS,RESERVED)" + 
                                " values(" +
                                "'" + datas[0] + "'," +
                                "'" + datas[1] + "'," +
                                "'" + datas[2] + "'," +
                                "'" + datas[3] + "'," +
                                "'" + datas[4] + "'," +
                                "'" + datas[5] + "'," +
                                "'" + datas[6] + "'," +
                                "'" + datas[7] + "'," +
                                "'" + datas[8] + "')";

            out.println(sqlCommand + "<br/>");

            if(1 == statement.executeUpdate(sqlCommand))
                return "true";
        }
        
        //新增排程
       
        else if(action.type.equals(Action.TYPE_SCHEDUAL)){
        
        	String [] datas = action.data2.split("`");
           	out.println("2.insertDB()<br/>"+action.data2);
           	
          
            String sqlCommand = "insert into " + TABLE_SCHEDUAL + "(SCH,TIMEBEG,TIMEEND,WORKITEM,STATUS,MEMBERID)" + 
                                " values(" +
                                "'" + datas[0] + "'," +
                                "'" + datas[1] + "'," +
                                "'" + datas[2] + "'," + 
                                "'" + datas[3] + "'," +     
                                "'" + datas[4] + "'," +                                              
                                "'" + datas[5] + "')";
                               
            /*                    
              String sqlCommand = "insert into " + TABLE_SCHEDUAL + "(SCH,TIMEBEG,TIMEEND,WORKITEM,STATUS,MEMBERID)" + 
                                " values(" +
                                "'固定行程'," +
                                "'9時00分'," +
                                "'10時00分'," +                                                    
                                "'掃地')";   
                                */                

            out.println("3"+sqlCommand + "<br/>");

            if(1 == statement.executeUpdate(sqlCommand))
                return "true";
        	
        	
        }
        //新增健康記錄
        else if(action.type.equals(Action.TYPE_HEALTH)){
        	String [] datas = action.data2.split("`");
             out.println("2.insertDB()Health=<br/>"+action.data2);
            String sqlCommand = "insert into " + TABLE_HEALTH + "(blood_glucose,blood_pressure,palpitant,pulse,datetime,status,MEMBERID)" + 
                                " values(" +
                                "'" + datas[0] + "'," +
                                "'" + datas[1] + "'," +
                                "'" + datas[2] + "'," +
                                "'" + datas[3] + "'," +  
                                "'" + datas[4] + "'," + 
                                "'" + datas[5] + "'," +                         
                                "'" + datas[6] + "')";

            out.println(sqlCommand + "<br/>");

            if(1 == statement.executeUpdate(sqlCommand))
                return "true";
        	
        	
        }
        
        // 新增外傭GPS
        
        else if(action.type.equals(Action.TYPE_GPS)){
        	  String [] datas = action.data2.split("`");
             out.println("2.insertDB()GPS=<br/>"+action.data2);
            String sqlCommand = "insert into " + TABLE_GPS + "(Latitude,Longitude,status,MEMBERID)" + 
                                " values(" +"'" + datas[0] + "','" + datas[1] + "','"  + datas[2] + "','" + datas[3]+ "')";

            out.println(sqlCommand + "<br/>");

            if(1 == statement.executeUpdate(sqlCommand))
                return "true";
        }
       
       // 新增照護資料
        else
        {
        }

        return "false";
    }

    // 查詢一筆會員資料或看護資料
    private String queryDB(Statement statement, Action action) throws SQLException
    {
        out.println("queryDB()<br/>");

        // 查詢會員資料
        if(action.type.equals(Action.TYPE_MEMBER))
        {
            ResultSet rs = statement.executeQuery("select * from " + TABLE_MEMBER + " where MEMBERID='" + action.data1 + "'");
            
            if(rs.next()) // ResultSet has data
            {
                int _id = rs.getInt(1);
                String number = rs.getString(2);
                String password = rs.getString(3);
                String email = rs.getString(4);
                String phone = rs.getString(5);
                String LAB_NUMBER = rs.getString(6);
                String LAB_PASSWORD = rs.getString(7);
                String REG_DATE = rs.getString(8);				
                String status = rs.getString(9);
                String reserved = rs.getString(10);

                
                return "" + _id + "`" + number + "`" + password + "`" + email + "`" + phone + "`" + LAB_NUMBER + "`" + LAB_PASSWORD + "`" + REG_DATE + "`" + status + "`" + reserved;
            }
            else // ResultSet是空的
                out.println("錯誤：資料庫中找不到資料「" + action.data1 + "」<br/>");
        }
        
         else if(action.type.equals(Action.TYPE_SCHEDUAL))
        {
        	  out.println(Action.TYPE_SCHEDUAL+" - queryDB()<br/>");
      ResultSet rs = statement.executeQuery("select * from " + TABLE_SCHEDUAL + " where MEMBERID='" + action.data1 + "'");
              out.println("select * from " + TABLE_SCHEDUAL + " where MEMBERID='" + action.data1 + "' and status='NEW' "+"<br/>");
              //data1=MEMBERID 會員編號要相同 ,  status 設定NEW =>要取回的資料
        if(rs.next())
      				 {
      				 	int _id = rs.getInt(1);
                String sch = rs.getString(2);
                String timebeg = rs.getString(3);
                String timeend = rs.getString(4);
                String workitem = rs.getString(5);
                String status =rs.getString(6);
								String MEMBERID =rs.getString(7);
								
                return "" + _id + "`" + sch + "`" + timebeg + "`" + timeend + "`" + workitem + "`" +status+ "`"+MEMBERID;
                
               }
        
          else{    
           	// ResultSet是空的
                return "錯誤：資料庫中找不到資料「" + action.data1 +"select * from " + TABLE_SCHEDUAL + " where MEMBERID='" + action.data1 + "' and status='NEW'"+ "」";  
     			}
        
      }
        
        
      //查健康資料
        else if(action.type.equals(Action.TYPE_HEALTH)){
      ResultSet rs = statement.executeQuery("select * from " + TABLE_HEALTH + " where MEMBERID='" + action.data1 + "'");
            
            if(rs.next()) // ResultSet有資料
            {
                int _id = rs.getInt(1);
                String blood_glucose = rs.getString(2);
                String blood_pressure = rs.getString(3);
                String palpitant = rs.getString(4);
                String pulse = rs.getString(5);
                String datetime = rs.getString(6);
								String status =rs.getString(6);
								String MEMBERID =rs.getString(7);
                
                return "" + _id + "`" + blood_glucose + "`" + blood_pressure + "`" + palpitant + "`" + pulse +"`"+datetime+ "`" +status+ "`"+MEMBERID;
            }
            else // ResultSet是空的
                out.println("錯誤：健康資料庫中找不到資料「" + action.data1 + "」<br/>");
     	
        }
        
        // 查詢照護資料
        else
        {
            return "true";
        }

        return "false111";
    }

    // 更新一筆會員資料或看護資料
    private String updateDB(Statement statement, Action action) throws SQLException
    {
        out.println("updateDB()<br/>");
        if(action.type.equals(Action.TYPE_MEMBER))
        {
        	 
            ResultSet rs = statement.executeQuery("select * from " + TABLE_MEMBER + " where NUMBER='" + action.data1 + "'");
            
            if(rs.next()) // ResultSet有資料
            {
				String [] datas = action.data2.split("`");
                String sqlCommand = "update " + TABLE_MEMBER +  
                                " set " +
                                "NAME='" + datas[0] + "'," +
                                "PASSWORD='" + datas[1] + "'," +
                                "EMAIL='" + datas[2] + "'," +
                                "PHONE='" + datas[3] + "'," +
                                "LAB_NUMBER='" + datas[4] + "'," +
                                "LAB_PASSWORD='" + datas[5] + "'," +
                                "REG_DATE='" + datas[6] + "'," +
                                "STATUS='" + datas[7] + "'," +
                                "RESERVED=" + datas[8] + "' where NUMBER='" + action.data1 + "'";

                out.println(sqlCommand + "<br/>");

                if(1 <= statement.executeUpdate(sqlCommand))
                    return "true";
            }
            else
            {
                out.println("錯誤：資料庫中找不到資料「" + action.data1 + "」<br/>");
                return "false";
            }
        }
        
        //更新排程
        else if(action.type.equals(Action.TYPE_SCHEDUAL))
              {
              	String [] data1s = action.data1.split("`");
                  ResultSet rs = statement.executeQuery("select * from " + TABLE_SCHEDUAL + " where _ID='" + data1s[0] + "' and MEMBERID='"+data1s[1]+"'");
                  
                  if(rs.next()) // ResultSet有資料
                  {
      				String [] datas = action.data2.split("`");
                      String sqlCommand = "update " + TABLE_SCHEDUAL +  
                                      " set " +                                
                                      "SCH='" + datas[1] + "'," +
                                      "TIMEBEG='" + datas[2] + "'," +
                                      "TIMEEND='" + datas[3] + "'," +
                                      "WORKITEM=" + datas[4] + "' where _ID='" + data1s[0] + "' and MEMBERID='"+data1s[1]+"'";

                      out.println(sqlCommand + "<br/>");

                      if(1 <= statement.executeUpdate(sqlCommand))
                          return "true";
                  }
                  else
                  {
                      out.println("錯誤：排程資料庫中找不到資料「" + action.data1 + "」<br/>");
                      return "false";
                  }
        }
      //更新健康資料
        else if(action.type.equals(Action.TYPE_HEALTH))
              {
                  String [] data1s = action.data1.split("`");
                  ResultSet rs = statement.executeQuery("select * from " + TABLE_HEALTH + " where _ID='" + data1s[0] + "' and MEMBERID='"+data1s[1]+"'");
                  
                  if(rs.next()) // ResultSet有資料
                  {
      				String [] datas = action.data2.split("`");
                      String sqlCommand = "update " + TABLE_HEALTH +  
                                      " set " +
                                      "blood_glucose='" + datas[1] + "'," +
                                      "blood_pressure='" + datas[2] + "'," +
                                      "palpitant='" + datas[3] + "'," +
                                      "pulse='" + datas[4] + "'," +                                     
                                      "datetime='" + datas[5] + "' where _ID='" + data1s[0] + "' and MEMBERID='"+data1s[1]+"'";

                      out.println(sqlCommand + "<br/>");

                      if(1 <= statement.executeUpdate(sqlCommand))
                          return "true";
                  }
                  else
                  {
                      out.println("錯誤：健康資料庫中找不到資料「" + action.data1 + "」<br/>");
                      return "false";
                  }
        }
        //更新GPS資料
        else if(action.type.equals(Action.TYPE_GPS))
              {
              	
                  ResultSet rs = statement.executeQuery("select * from " + TABLE_GPS +   "' where MEMBERID='"+action.data1+"'");
                  
                  if(rs.next()) // ResultSet有資料
                  {
      				String [] datas = action.data2.split("`");
                      String sqlCommand = "update " + TABLE_HEALTH +  
                                      " set " +
                                      "Latitude='" + datas[0] + "'," +
                                                                      
                                      "Longitude=" + datas[1] + "' where _ID='" + action.data1 + "'";

                      out.println(sqlCommand + "<br/>");

                      if(1 <= statement.executeUpdate(sqlCommand))
                          return "true";
                  }
                  else
                  {
                      out.println("錯誤：健康資料庫中找不到資料「" + action.data1 + "」<br/>");
                      return "false";
                  }
        } 
        
        
        

        // 更新照護資料
        else
        {
            return "true";
        }

        return "false";
    }

    // 刪除一筆會員資料或一筆看護資料
    private String deleteDB(Statement statement, Action action) throws SQLException
    {
        out.println("deleteDB()<br/>");
        
        // 刪除會員資料
        if(action.type.equals(Action.TYPE_MEMBER))
        {
            if(1 == statement.executeUpdate("delete from " + TABLE_MEMBER + " where NUMBER='" + action.data1 + "'"))
                return "true";
        }
        
        // Ann 刪除排程資料
        else if(action.type.equals(Action.TYPE_SCHEDUAL))
        {
            if(1 == statement.executeUpdate("delete * from " + TABLE_SCHEDUAL + " where _ID='" + action.data1 + "'"))
                return "true";
        }
        // Ann 刪除健康資料
        else if(action.type.equals(Action.TYPE_HEALTH))
        {
            if(1 == statement.executeUpdate("delete * from " + TABLE_SCHEDUAL + " where _ID='" + action.data1 + "'"))
                return "true";
        }

        // 刪除照護資料
        else
        {
            if(1 == statement.executeUpdate("delete from " + action.type + " where NUMBER='" + action.data1 + "'"))
                return "true";
        }

        return "true";
    }

    // 根據TYPE來回傳會員資料或看護資料資料筆數 
    private String countDB(Statement statement, Action action) throws SQLException
    {
        out.println(action.type+"countDB()<br/>");
        // 排程明細
        if(action.type.equals(Action.TYPE_SCHEDUAL))
        {
        	ResultSet rs = statement.executeQuery("select * from SCHDUL");
        	out.println(action.type+"countDB()<br/>");
        	out.println(statement+"countDB()<br/>");
        	int count=0;
        	while(rs.next()){
        		 out.println( (count+1)+". " +rs.getString(2) +rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6)+rs.getString(7)+"<br/>");
     		++count;
        	}
                return count+"筆數";
        }
        
        // 健康明細
        if(action.type.equals(Action.TYPE_HEALTH))
        {
        	ResultSet rs = statement.executeQuery("select * from health");
        	
        	int count=0;
        	while(rs.next()){
        		 out.println( (count+1)+rs.getString(2) +rs.getString(3)+rs.getString(4)+"<br/>");
        		++count;
        	}
                return count+"筆數";
        }
        // GPS明細
        if(action.type.equals(Action.TYPE_GPS))
        {
        	ResultSet rs = statement.executeQuery("select * from GPS");
        	out.println(statement+"countDB()<br/>");
        	int count=0;
        	while(rs.next()){
        		 out.println( (count+1)+rs.getString(2) +rs.getString(3)+"<br/>");
        		++count;
        	}
                return count+"筆數";
        }

        return "no data";
        
    }      
      


    // 看護與僱主的連結
    private String linkDB(Statement statement, Action action) throws SQLException
    {
        out.println("linkDB()<br/>");

        if(action.type.equals(Action.TYPE_LINK))
        {
            // 查詢雇主資料
            ResultSet emp_rs = statement.executeQuery("select * from " + TABLE_MEMBER + " where NUMBER='" + action.data1 + "'");

            if(emp_rs.next()) // ResultSet有資料
            {
                String type = emp_rs.getString(5);
                String status = emp_rs.getString(15);
                String link = emp_rs.getString(16);

                out.println(type + "," + status + "," + link + "<br/>");
                // 確認身份
                if(!type.equals(Action.TYPE_EMPLOYER) || !link.equals("NULL") || !status.equals("normal")) return "false.1";
            }
            else return "false.2";

            // 查詢看護資料
            ResultSet nurse_rs = statement.executeQuery("select * from " + TABLE_MEMBER + " where NUMBER='" + action.data2 + "'");

            if(nurse_rs.next()) // ResultSet有資料
            {
                String type = nurse_rs.getString(5);
                String status = nurse_rs.getString(15);
                String link = nurse_rs.getString(16);
                out.println(type + "," + status + "," + link + "<br/>");

                // 確認身份
                if(!type.equals(Action.TYPE_NURSE) || !link.equals("NULL") || !status.equals("normal")) return "false.3";
            }
            else return "false.4";
            // 連結
            statement.executeUpdate("update " + TABLE_MEMBER + " set LINK='"+ action.data2 + "' where NUMBER='" + action.data1 + "'");
            statement.executeUpdate("update " + TABLE_MEMBER + " set LINK='"+ action.data1 + "' where NUMBER='" + action.data2 + "'");
        }
        else if(action.type.equals(Action.TYPE_UNLINK))
        {
            // 查詢雇主資料
            ResultSet emp_rs = statement.executeQuery("select * from " + TABLE_MEMBER + " where NUMBER='" + action.data1 + "'");
        
            String link_emp, link_nurse;

            if(emp_rs.next()) // ResultSet有資料
            {
                String type = emp_rs.getString(5);
                String status = emp_rs.getString(15);
                link_emp = emp_rs.getString(16);
                out.println(type + "," + status + "," + link_emp + "<br/>");

                // 確認身份
                if(!type.equals(Action.TYPE_EMPLOYER) || !status.equals("normal")) return "false.5";
            }
            else return "false.6";

            // 查詢看護資料
            ResultSet nurse_rs = statement.executeQuery("select * from " + TABLE_MEMBER + " where NUMBER='" + action.data2 + "'");

            if(nurse_rs.next()) // ResultSet有資料
            {
                String type = nurse_rs.getString(5);
                String status = nurse_rs.getString(15);
                link_nurse = nurse_rs.getString(16);
                out.println(type + "," + status + "," + link_nurse + "<br/>");

                // 確認身份
                if(!type.equals(Action.TYPE_NURSE) || !status.equals("normal")) return "false.7";
            }
            else return "false.8";

            // 連結有問題
            if(!link_emp.equals(action.data2)) return "false.9";
            if(!link_nurse.equals(action.data1)) return "false.10";
            
            // 取消連結
            statement.executeUpdate("update " + TABLE_MEMBER + " set LINK='NULL' where NUMBER='" + action.data1 + "'");
            statement.executeUpdate("update " + TABLE_MEMBER + " set LINK='NULL' where NUMBER='" + action.data2 + "'");
        }
        else
            return "false.11";

        return "true";
    }
}
