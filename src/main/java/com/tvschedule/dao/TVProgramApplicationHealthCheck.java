package com.tvschedule.dao;

import com.codahale.metrics.health.HealthCheck;

public class TVProgramApplicationHealthCheck  extends HealthCheck {
    private  final TVProgramDB tvProgramDB;

    public TVProgramApplicationHealthCheck(TVProgramDB tvProgramDB) {
        this.tvProgramDB = tvProgramDB;
    }

    @Override
    protected Result check() throws Exception {
        String tvProgramHealthStatus = tvProgramDB.performHealthCheck();
        if (tvProgramHealthStatus == null) {
            return Result.healthy("HEALTHY");
        } else {
            return Result.unhealthy("UNHEALTHY", tvProgramHealthStatus);
        }
    }
}
