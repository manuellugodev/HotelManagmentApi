package com.manuellugodev.hotel.entity;

public class DashboardStats {
    private long totalUsers;
    private long totalGuests;
    private long totalRooms;
    private long totalAppointments;
    private long todayCheckIns;
    private long todayCheckOuts;
    private long activeAppointments;
    private long pendingAppointments;

    public DashboardStats() {
    }

    public DashboardStats(long totalUsers, long totalGuests, long totalRooms, long totalAppointments,
                         long todayCheckIns, long todayCheckOuts, long activeAppointments, long pendingAppointments) {
        this.totalUsers = totalUsers;
        this.totalGuests = totalGuests;
        this.totalRooms = totalRooms;
        this.totalAppointments = totalAppointments;
        this.todayCheckIns = todayCheckIns;
        this.todayCheckOuts = todayCheckOuts;
        this.activeAppointments = activeAppointments;
        this.pendingAppointments = pendingAppointments;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalGuests() {
        return totalGuests;
    }

    public void setTotalGuests(long totalGuests) {
        this.totalGuests = totalGuests;
    }

    public long getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(long totalRooms) {
        this.totalRooms = totalRooms;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getTodayCheckIns() {
        return todayCheckIns;
    }

    public void setTodayCheckIns(long todayCheckIns) {
        this.todayCheckIns = todayCheckIns;
    }

    public long getTodayCheckOuts() {
        return todayCheckOuts;
    }

    public void setTodayCheckOuts(long todayCheckOuts) {
        this.todayCheckOuts = todayCheckOuts;
    }

    public long getActiveAppointments() {
        return activeAppointments;
    }

    public void setActiveAppointments(long activeAppointments) {
        this.activeAppointments = activeAppointments;
    }

    public long getPendingAppointments() {
        return pendingAppointments;
    }

    public void setPendingAppointments(long pendingAppointments) {
        this.pendingAppointments = pendingAppointments;
    }
}
