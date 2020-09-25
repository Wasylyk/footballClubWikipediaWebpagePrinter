package xtm.task;

import xtm.task.service.FootBallClubInfoService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        FootBallClubInfoService footBallClubInfoService = new FootBallClubInfoService();
        List<String> links = footBallClubInfoService.searchLinks("liverpool");
        for (String link : links)
            System.out.println(link);
    }
}
