package helpers;

import com.sun.net.httpserver.HttpExchange;
import dao.DBException;
import dao.SessionDAO;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CookieHelper {
    //this class contains methods to proceed cookie, get session id from it, create list with all possible cookies
    // find cookie by name (not index)

    SessionDAO sessionDAO = new SessionDAO();

    //this method return userId form db (sessions table) found by session id taken from cookie
    public int getUserIdBySessionID(HttpExchange httpExchange){
        int mentorId = 0;

        //if there is more than one cookie:
        Optional<HttpCookie> cookie = this.getCookieWithSessionId(httpExchange);
        String wrongSessionId = cookie.get().getValue();
        String sessionId = removeQuotationFromSessionId(wrongSessionId);
        System.out.println("sessionID: " + sessionId);


        try{
            mentorId = sessionDAO.getUserIdBySession(sessionId);
            System.out.println("mentor id: " + mentorId);
        }catch (DBException exc){
            System.out.println("DB exception cought in getUsernBySessionID");
        }
        return mentorId;
    }


    //this method create list with all cookies, in case if there is more than one cookie
    private List<HttpCookie> parseCookies(String cookieString){
        List<HttpCookie> cookies = new ArrayList<>();
        if(cookieString == null || cookieString.isEmpty()){ // what happens if cookieString = null?
            return cookies;
        }
        for(String cookie : cookieString.split(";")){
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1, cookie.length());
            cookies.add(new HttpCookie(cookieName, cookieValue));
        }
        return cookies;
    }

    public String removeQuotationFromSessionId(String cookieString){
        String[] cookieValues = cookieString.split("=");
        String sessionIdwrong = cookieValues[0].trim();
        StringBuilder sb = new StringBuilder(sessionIdwrong);
        sb.deleteCharAt(sessionIdwrong.length()-1);
        sb.deleteCharAt(0);
        String sessionId = sb.toString();
        //System.out.println(sessionId + "session id in removequotation marks");
        return sessionId;
    }

    //this method return one cookie from list with session id (in case of sending more than one cookie)
    private Optional<HttpCookie> getCookieWithSessionId(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = this.parseCookies(cookieStr);
        return this.findCookieByName("sessionId", cookies);
    }



    //this method return one cookie by its name
    private Optional<HttpCookie> findCookieByName(String name, List<HttpCookie> cookies){
        for(HttpCookie cookie : cookies){
            if(cookie.getName().equals(name))
                return Optional.ofNullable(cookie);
        }
        return Optional.empty();
    }
}
