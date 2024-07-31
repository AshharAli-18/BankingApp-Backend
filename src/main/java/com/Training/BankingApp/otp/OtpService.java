package com.Training.BankingApp.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailSenderService emailService;

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public String generateAndSendOtp(String email) {
        String generatedOtp = generateOtp();
        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setOtp(generatedOtp);
        otp.setExpiration(LocalDateTime.now().plusMinutes(2));
        otpRepository.save(otp);
        emailService.sendEmail(email, "OTP Generated", "Your generated otp is: " + generatedOtp);
        return generatedOtp;
    }



    public boolean validateOtp(String email, String otp) {
        Otp savedOtp = otpRepository.findByEmailAndOtp(email, otp);
        if (savedOtp != null && LocalDateTime.now().isBefore(savedOtp.getExpiration())) {
            otpRepository.delete(savedOtp); // OTP can be used only once
            return true;
        }
        return false;
    }
}
