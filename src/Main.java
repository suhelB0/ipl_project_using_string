public class Main{
    public static void main(String[] args) {
        IPLAnalyzer iplAnalyzer = new IPLAnalyzer();

        iplAnalyzer.loadMatchData();
        iplAnalyzer.loadDeliveriesData();

        iplAnalyzer.getMatchesPerYear();

        iplAnalyzer.getMatchesWonPerTeam();

        iplAnalyzer.getExtraRunsConcededPerTeam2016();

        iplAnalyzer.getTopEconomicalBowlers2015();

        iplAnalyzer.getTopScorer2016();
    }
}