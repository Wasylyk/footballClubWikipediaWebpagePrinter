package xtm.task;

import xtm.task.service.FootBallClubInfoService;

public class Main {

    public static void main(String[] args) {
        FootBallClubInfoService footBallClubInfoService = new FootBallClubInfoService();
        System.out.println(footBallClubInfoService.findLinkToClub("liverpool"));
    }
}