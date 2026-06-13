package com.mydietplan.service;

import java.time.LocalDate;

public interface HistoryService {
    void showHistoryByDate(String userId, LocalDate date);
}
