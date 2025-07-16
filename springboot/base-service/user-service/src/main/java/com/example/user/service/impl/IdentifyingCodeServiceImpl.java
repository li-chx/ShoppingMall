package com.example.user.service.impl;

import com.example.user.service.IdentifyingCodeService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

@Service
public class IdentifyingCodeServiceImpl implements IdentifyingCodeService {

    Dictionary<String, IdentifyingCodeStructure> data = new Hashtable<>();

    @Transactional
    @Override
    public String getIdentifyingCode(String email, IdentifyingCodeType type, long expireTimeInSeconds) {
        dataClear();
        var structure = data.get(IdentifyingCodeStructure.getId(email, type));
        if (structure != null && System.currentTimeMillis() / 1000 < structure.startTimeInSeconds + 60)
            return null;
        structure = IdentifyingCodeStructure.createIdentifyingCodeStructure(email, type, expireTimeInSeconds);
        data.put(structure.id, structure);
        return structure.code;
    }

    @Transactional
    @Override
    public boolean testIdentifyingCode(String email, IdentifyingCodeType type, String code) {
        dataClear();
        var structure = data.get(IdentifyingCodeStructure.getId(email, type));
        if (structure == null) {
            return false;
        }
        if (structure.isExpired()) {
            data.remove(structure.id);
            return false;
        }
        if (structure.code.equals(code)) {
            data.remove(structure.id);
            return true;
        }
        return false;
    }


    private void dataClear() {
        var keys = data.keys();
        while (keys.hasMoreElements()) {
            var key = keys.nextElement();
            var structure = data.get(key);
            if (structure.isExpired()) {
                data.remove(key);
            }
        }
    }
}

@NoArgsConstructor
class IdentifyingCodeStructure {
    public String id;
    public String username;
    public IdentifyingCodeService.IdentifyingCodeType type;
    public String code;
    public long startTimeInSeconds;
    public long expireTimeInSeconds;

    boolean isExpired() {
        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        return currentTimeInSeconds > startTimeInSeconds + expireTimeInSeconds;
    }

    static String getId(String username, IdentifyingCodeService.IdentifyingCodeType type) {
        return username + type;
    }

    static IdentifyingCodeStructure createIdentifyingCodeStructure(String email, IdentifyingCodeService.IdentifyingCodeType type, long expireTimeInSeconds) {
        IdentifyingCodeStructure identifyingCodeStructure = new IdentifyingCodeStructure();
        identifyingCodeStructure.id = email + type;
        identifyingCodeStructure.username = email;
        identifyingCodeStructure.type = type;
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000); // 范围是 100000 到 999999
        identifyingCodeStructure.code = "" + randomNumber;
        identifyingCodeStructure.startTimeInSeconds = System.currentTimeMillis() / 1000;
        identifyingCodeStructure.expireTimeInSeconds = expireTimeInSeconds;
        return identifyingCodeStructure;
    }
}