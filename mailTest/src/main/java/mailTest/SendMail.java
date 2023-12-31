package mailTest;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public static String naverMailSend(String email) {
        String verificationCode = "";
        String host = "smtp.naver.com"; // 네이버 SMTP 서버
        String user = "yu_limmi_@naver.com"; // 네이버 계정
        String password = ""; // 네이버 계정 비밀번호

        // SMTP 서버 정보를 설정한다.
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.naver.com");

        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // 메일 제목
            message.setSubject("SMTP TEST");

            // 인증 번호 생성
            verificationCode = generateVerificationCode();

            // 메일 내용에 인증 번호 추가
            String mailContent = "TEST MAIL \n인증 번호는 " + verificationCode + "입니다.";
            message.setText(mailContent);

            // 메일 전송
            Transport.send(message);
            System.out.println("Success Message Sent");

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return verificationCode;
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 6자리의 난수 생성
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }


}
