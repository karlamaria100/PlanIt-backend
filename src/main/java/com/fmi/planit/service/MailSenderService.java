package com.fmi.planit.service;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Properties;

@Service
public class MailSenderService {

    public void sendVerificationMail(String email, String message, String subject, String token, String purpose){
        SendMailTLS sendMailTLS = new SendMailTLS(email, message, subject, token, purpose);
        Thread th = new Thread(sendMailTLS);
        th.start();
    }

    public void sendForgotPasswordMail(String email, String message, String subject, String token, String purpose){
        SendMailTLS sendMailTLS = new SendMailTLS(email, message, subject, token, purpose);
        Thread th = new Thread(sendMailTLS);
        th.start();
    }

    public class SendMailTLS implements Runnable{

        private String email;
        private String message;
        private String subject;
        private String token;
        private String purpose;

        public SendMailTLS(String email, String message, String subject, String token, String purpose) {
            this.email = email;
            this.message = message;
            this.subject = subject;
            this.token = token;
            this.purpose = purpose;
        }

        @Override
        public void run(){

            final String username = "timemanagement@cst-dev.com";
            final String password = "12345678";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "mail.cst-dev.com");
            props.put("mail.smtp.port", "587");

            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
            } catch (GeneralSecurityException e1) {

                e1.printStackTrace();
            }
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("timemanagement@cst-dev.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(this.email));
                message.setSubject(this.subject);

                String deepLink = null;
                try {
                    deepLink = getLinkFromBranch(this.token);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(this.purpose.equals("confirmation"))
                    message.setContent( "<html>" + "<head>" +
                            "<style>\n" +
                            ".button {\n" +
                            "    background-color: #ee5d2c;\n" +
                            "    border: none;\n" +
                            "    color: white;\n" +
                            "    padding: 10px 12px;\n" +
                            "    text-align: center;\n" +
                            "    text-decoration: none;\n" +
                            "    display: inline-block;\n" +
                            "    font-size: 16px;\n" +
                            "    margin: 0px 2px;\n" +
                            "    -webkit-transition-duration: 0.4s; /* Safari */\n" +
                            "    transition-duration: 0.4s;\n" +
                            "    cursor: pointer;\n" +
                            "}" + "\n" +
                            ".button:hover {\n" +
                            "    background-color: #ef480c;\n" +
                            "    color: white;\n" +
                            "}" + "\n" + "</style>" + "</head>" +
                            "<body style='text-align:center;'>" + "<img src=\"http://imgur.com/xNGZjLW.png\" alt=\"W3Schools.com\" style=\"margin:0 auto;width:355px;height:80px;\">" + "<br>" + "<br>" +
                            "<h2 style='text-align:center;'>" + "Your're almost there." + "</h2>" +
                            "<a href=" + deepLink + "?token=" + this.token +">" + "Click here " + "</a>"+ "to confirm your account! Or click down below. " + "<br>" + "<br>" +
                            "<a href=" + deepLink + "?token=" + this.token + " class='button'>" + "YES, SING ME UP!" +
                            "</a>" + "<br>" + "<br>" +
                            "You will need this " + "token: " + "<span style='color:#ef480c; font-weight: bold;'>" + this.token + "</span>" +
                            " to confirm your account for our mobile app." + "<br>" + "</body>", "text/html");
                    //Forgot password.
                else message.setContent("<body style='text-align:center;'>" +
                        "<img src=\"http://imgur.com/xNGZjLW.png\" alt=\"W3Schools.com\" style=\"width:355px;height:80px;\">" + "<br>" +
                        "<h2 style='text-align:center;'>" + "Forgot password?!" + "</h2>" +
                        "<a href=http://cst-dev.com:8085/timeManagement/" + this.purpose + "?token=" +
                        this.token +">" + "Click here" + "</a>" + " to reset your password!" +"<br>" + "<br>" + "You will need this " + "token: " + "<span style='color:#ef480c; font-weight: bold;'>" + this.token + "</span>" +
                        " to reset your password for our mobile app." + "</body>", "text/html");
                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) {
        try {
            String token = "token-data";
            System.out.println(getLinkFromBranch(token));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getLinkFromBranch(String token) throws IOException {

        String url = "https://api.branch.io/v1/url";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        String jsonRequestBody = "{\n" +
                "\t\"branch_key\":\"key_test_fhCv6EVT08JEKYWaVcZlIbmbBsefexn6\", \n" +
                "\t\"data\":\"{\\\"token\\\":\\\"" + token + "\\\"}\"\n" +
                "}";
        HttpEntity entity = new ByteArrayEntity(jsonRequestBody.getBytes("UTF-8"));
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        JSONObject jsonRepose = new JSONObject(result.toString());

        return jsonRepose.getString("url");
    }


}
