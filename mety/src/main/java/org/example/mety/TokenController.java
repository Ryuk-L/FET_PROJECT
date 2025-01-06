package org.example.mety;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.agora.rtc.RtcTokenBuilder;
import com.agora.rtc.RtcRole;

@RestController
@RequestMapping("/api")
public class TokenController {

    @GetMapping("/access_token")
    public ResponseEntity<Object> generateToken(@RequestParam String channel, @RequestParam(required = false) String role, @RequestParam(required = false) Integer expire) {
        if (channel == null || channel.isEmpty()) {
            return ResponseEntity.status(400).body("Channel is required");
        }

        if (expire == null) {
            expire = 3600; // Default expiration time
        }

        RtcRole rtcRole = role != null && role.equals("publisher") ? RtcRole.PUBLISHER : RtcRole.SUBSCRIBER;
        long currentTime = System.currentTimeMillis() / 1000;
        long privilegeExpire = currentTime + expire;
        String token = RtcTokenBuilder.buildTokenWithUid("app_id", "certificate", channel, 0, rtcRole, privilegeExpire);

        return ResponseEntity.ok().body(token);
    }
}
