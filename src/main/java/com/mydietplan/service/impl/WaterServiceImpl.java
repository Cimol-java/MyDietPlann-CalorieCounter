package com.mydietplan.service.impl;

import com.mydietplan.model.WaterLog;
import com.mydietplan.repository.WaterRepository;
import com.mydietplan.service.WaterService;
import com.mydietplan.util.DateUtil;
import com.mydietplan.util.IdGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WaterServiceImpl implements WaterService {
    private final WaterRepository waterRepository;

    public WaterServiceImpl(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }

    @Override
    public void addWater(String userId, double amountMl) {
        String id = IdGenerator.generate();
        LocalDateTime now = LocalDateTime.now();
        WaterLog log = new WaterLog(id, userId, now, amountMl);
        waterRepository.save(userId, log);
    }

    @Override
    public boolean editWater(String userId, String logId, double amountMl) {
        WaterLog log = waterRepository.findById(userId, logId);
        if (log == null) return false;
        log.setAmountMl(amountMl);
        waterRepository.update(userId, log);
        return true;
    }

    @Override
    public boolean deleteWater(String userId, String logId) {
        return waterRepository.delete(userId, logId);
    }

    @Override
    public List<WaterLog> getTodayWaters(String userId) {
        LocalDate today = DateUtil.today();
        List<WaterLog> allUserLogs = waterRepository.findByUserId(userId);
        List<WaterLog> todayLogs = new ArrayList<>();
        for (WaterLog log : allUserLogs) {
            if (log.getLoggedAt().toLocalDate().equals(today)) {
                todayLogs.add(log);
            }
        }
        return todayLogs;
    }
}
