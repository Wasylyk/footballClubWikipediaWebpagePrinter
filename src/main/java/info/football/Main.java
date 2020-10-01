package info.football;

import info.football.service.FootBallClubInfoService;

public class Main {

    public static void main(String[] args) {

        FootBallClubInfoService footBallClubInfoService = new FootBallClubInfoService();
        System.out.println(footBallClubInfoService.findLinkToClub(args[0]));
    }
}