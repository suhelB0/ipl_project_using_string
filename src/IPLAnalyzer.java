import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IPLAnalyzer {
    private final List<String[]> matchesData;
    private final List<String[]> deliveriesData;

    public IPLAnalyzer() {
        matchesData = new ArrayList<>();
        deliveriesData = new ArrayList<>();
    }

    // Loading the match data
    public void loadMatchData(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("data/matches.csv"))){
            String line = null;
            boolean first = true;

            while((line = bufferedReader.readLine()) != null){
                if(first){
                    first = false;
                    continue;
                }
                matchesData.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Cannot find a file "+e);
        }
    }

    // Load the delivery data
    public void loadDeliveriesData(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("data/deliveries.csv"))){
            String line = null;
            boolean first = true;

            while((line = bufferedReader.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                deliveriesData.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Cannot find a file "+e);
        }
    }

    // Number of matches played per year of all the years in IPL.
    public void getMatchesPerYear(){
        Map<String, Integer> matchesPerYear = new TreeMap<>();
        for(String[] match : matchesData){
            String season = match[1];

            matchesPerYear.put(season, matchesPerYear.getOrDefault(season,0)+1);
        }

        System.out.println("Number of matches played per year of all the years in IPL.");
        for(Map.Entry<String, Integer> matchPerYear : matchesPerYear.entrySet()){
            System.out.println(matchPerYear.getKey()+" : "+matchPerYear.getValue());
        }
    }

    // Number of matches won of all teams over all the years of IPL.
    public void getMatchesWonPerTeam(){
        Map<String, Integer> matchesWonPerTeam = new HashMap<>();
        for(String[] match : matchesData){
            String winnerTeam = match[10];
            if(!winnerTeam.isBlank())
                matchesWonPerTeam.put(winnerTeam, matchesWonPerTeam.getOrDefault(winnerTeam,0)+1);
        }

        System.out.println("Number of matches won of all teams over all the years of IPL.");
        for(Map.Entry<String, Integer> matchWonPerTeam : matchesWonPerTeam.entrySet()){
            System.out.println(matchWonPerTeam.getKey()+" : "+matchWonPerTeam.getValue());
        }
    }

    // For the year 2016 get the extra runs conceded per team.
    public void getExtraRunsConcededPerTeam2016(){
        Set<String> matchIds2016 = new HashSet<>();
        for(String[] match : matchesData){
            String id = match[0];
            String season = match[1];
            if(season.equals("2016")){
                matchIds2016.add(id);
            }
        }

        Map<String, Integer> extraRunsConcededPerTeam = new HashMap<>();
        for(String[] delivery : deliveriesData){
            String id = delivery[0];
            if(matchIds2016.contains(id)){
                String bowlingTeam = delivery[3];
                int extraRuns = Integer.parseInt(delivery[16]);
                extraRunsConcededPerTeam.put(bowlingTeam, extraRunsConcededPerTeam.getOrDefault(bowlingTeam,0) + extraRuns);
            }
        }

        System.out.println("For the year 2016 get the extra runs conceded per team");
        for(Map.Entry<String, Integer> extraRuns : extraRunsConcededPerTeam.entrySet()){
            System.out.println(extraRuns.getKey()+" : "+extraRuns.getValue());
        }
    }

    // For the year 2015 get the top economical bowlers.
    public void getTopEconomicalBowlers2015(){
        Set<String> matchId2015 = new HashSet<>();
        for(String[] match : matchesData){
            String id = match[0];
            String season = match[1];
            if(season.equals("2015")){
                matchId2015.add(id);
            }
        }

        Map<String, Integer> runConceded = new HashMap<>();
        Map<String, Integer> ballsBowled = new HashMap<>();

        for(String[] delivery : deliveriesData){
            String id = delivery[0];
            if(matchId2015.contains(id)){
                String bowler = delivery[8];
                int runs = Integer.parseInt(delivery[17]);
                int balls = Integer.parseInt(delivery[5]);

                if(balls < 7){
                    ballsBowled.put(bowler, ballsBowled.getOrDefault(bowler,0)+1);
                }
                runConceded.put(bowler, runConceded.getOrDefault(bowler,0)+runs);
            }
        }

        Map<String, Double> economicBowlers = new HashMap<>();

        for(String bowler : runConceded.keySet()){
            int runs = runConceded.get(bowler);
            int balls = ballsBowled.get(bowler);
            double economy = (runs * 6.0)/balls;

            economicBowlers.put(bowler,economy);
        }

        List<Map.Entry<String,Double>> entryList = new ArrayList<>(economicBowlers.entrySet());

        Comparator<Map.Entry<String,Double>> comparator = new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };
        entryList.sort(comparator);
        for(Map.Entry<String, Double> economy : entryList){
            System.out.println(economy.getKey()+" : "+economy.getValue());
        }
    }

    // Top-Scorer in 2016 season
    public void getTopScorer2016(){
        Set<String> matchId2016 = new HashSet<>();
        for(String[] match : matchesData){
            String id = match[0];
            String season = match[1];
            if(season.equals("2016")){
                matchId2016.add(id);
            }
        }

        Map<String,Integer> batsmenScores = new HashMap<>();

        for(String[] delivery : deliveriesData){
            String id = delivery[0];
            if(matchId2016.contains(id)){
                String batsman = delivery[6];
                int score = Integer.parseInt(delivery[15]);
                batsmenScores.put(batsman, batsmenScores.getOrDefault(batsman,0)+score);
            }
        }
        System.out.println(batsmenScores);

        String batsman = null;
        int score = Integer.MIN_VALUE;
        for(Map.Entry<String, Integer> batsmenScore : batsmenScores.entrySet()){
            if(batsmenScore.getValue() > score){
                score = batsmenScore.getValue();
                batsman = batsmenScore.getKey();
            }
        }
        System.out.println(batsman+" : "+score);
    }
}
