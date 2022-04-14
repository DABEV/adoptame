package mx.edu.utez.adoptame.service;

public interface EmailService {
    boolean sendEmail(String emailTo, String emailSubject, String emailContent);
}
