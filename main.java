/* Programmer : Mj.Yazdani
   Email : Mj.Yazdani1988@gmail.com
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;


class Team {
    private int id;
    private String name;
    private int money;
    private ArrayList<Player> players = new ArrayList<Player>();
    private int win;
    private int loose;
    private int equal;

    public Team(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void removePlayer(Player p) {
        this.players.remove(p);
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public int getTeamPlayerCount() {
        return (int) this.players.stream().count();
    }

    public int getTeamSpeedFinishing() {
        int sum = 0;
        if (this.getTeamPlayerCount() >= 11) {
            /*for (int i=0 ; i++ ; i=10){sum=sum+this.players[i].}*/
            int i = 0;
            for (Player p : players) {
                sum = sum + p.getSpeed() + p.getFinishing();
                i++;
                if (i == 12) break;
            }

        }
        return sum;
    }

    public int getTeamSpeedDefence() {
        int sum = 0;
        if (this.getTeamPlayerCount() >= 11) {
            for (int i = 0; i < 11; i++) {
                sum = sum + this.players.get(i).getSpeed() + this.players.get(i).getDefence();
            }
        }
        return sum;
    }

    public int getWin() {
        return this.win;
    }

    public void setWin(int number) {
        this.win = number;
    }

    public int getLoose() {
        return this.loose;
    }

    public void setLoose(int number) {
        this.loose = number;
    }

    public int getEqual() {
        return this.equal;
    }

    public void setEqual(int number) {
        this.equal = number;
    }
}

class Player {
    private int id;
    private String name;
    private int price;
    private int speed;
    private int finishing;
    private int defence;
    private int teamId;

    public Player(int id, String name, int price, int speed, int finishing, int defence) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.finishing = finishing;
        this.defence = defence;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getFinishing() {
        return this.finishing;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getTeamId() {
        return this.teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}

public class Main {
    static ArrayList<Player> allPlayers = new ArrayList<Player>();
    static ArrayList<Team> allTeams = new ArrayList<Team>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int PlayerId = 1;
        int TeamId = 1;

        String myline = in.nextLine();
        while (!myline.contains("end")) {

            String[] lines = myline.split(" ");

            if (lines[0].equalsIgnoreCase("new")) {
                if (lines[1].equalsIgnoreCase("player")) {
                    Player player = new Player(PlayerId, lines[2], Integer.parseInt(lines[3]), Integer.parseInt(lines[4]), Integer.parseInt(lines[5]), Integer.parseInt(lines[6]));
                    allPlayers.add(player);
                    PlayerId++;
                } else if (lines[1].equalsIgnoreCase("team")) {
                    Team team = new Team(TeamId, lines[2], Integer.parseInt(lines[3]));
                    allTeams.add(team);
                    TeamId++;
                }
            }
            if (lines[0].equalsIgnoreCase("buy")) {
                int playerID = Integer.parseInt(lines[1]);
                int teamID = Integer.parseInt(lines[2]);

                Player pl = GetPlayer(playerID);
                Team tm = GetTeam(teamID);

                if (pl == null) {
                      System.out.println("player with the id " + playerID + " doesnt exist!");
                } else if (tm == null) {
                      System.out.println("team with the id " + teamID + " doesnt exist!");
                } else if (pl.getPrice() > tm.getMoney()) {
                      System.out.println("the team cant afford to buy this player");
                } else if (pl.getTeamId() > 0) {
                     System.out.println("player already has a team");
                } else {
                    pl.setTeamId(tm.getId());
                    tm.setMoney(tm.getMoney() - pl.getPrice());
                    tm.addPlayer(pl);
                    System.out.println("player added to the team succesfully");
                }
            }
            if (lines[0].equalsIgnoreCase("sell")) {
                int playerID = Integer.parseInt(lines[1]);
                int teamID = Integer.parseInt(lines[2]);

                Player pl = GetPlayer(playerID);
                Team tm = GetTeam(teamID);

                if (pl == null) {
                      System.out.println("player  doesnt exist!");
                } else if (tm == null) {
                     System.out.println("team doesnt exist");
                } else if (pl.getTeamId() != tm.getId()) {
                      System.out.println("team doesnt have this player");
                } else if (pl.getTeamId() == tm.getId()) {
                    pl.setTeamId(0);
                    tm.setMoney(tm.getMoney() + pl.getPrice());
                    tm.removePlayer(pl);
                    System.out.println("player sold succesfully");
                }
            }
            if (lines[0].equalsIgnoreCase("match")) {

                int teamID1 = Integer.parseInt(lines[1]);
                int teamID2 = Integer.parseInt(lines[2]);

                Team tm1 = GetTeam(teamID1);
                Team tm2 = GetTeam(teamID2);

                if (tm1 == null || tm2 == null) {
                      System.out.println("team doesnt exist");
                } else if (tm1 != null && tm2 != null && ( tm1.getTeamPlayerCount() < 11 || tm2.getTeamPlayerCount() < 11)) {
                      System.out.println("the game can not be held due to loss of the players");
                } else {
                    int tm1Power = tm1.getTeamSpeedFinishing();
                    int tm2Power = tm2.getTeamSpeedDefence();
                    // System.out.println("Team " + tm1.getId() + " power : " + tm1Power + " , Team " + tm2.getId() + " power :" + tm2Power);

                    if (tm1Power > tm2Power) {
                        tm1.setWin((int) tm1.getWin() + 1);
                        tm2.setLoose((int) tm2.getLoose() + 1);
                        tm1.setMoney((int) tm1.getMoney() + 1000);
                    } else if (tm1Power < tm2Power) {
                        tm1.setLoose((int) tm1.getLoose() + 1);
                        tm2.setWin((int) tm2.getWin() + 1);
                        tm2.setMoney((int) tm2.getMoney() + 1000);
                    } else if (tm1Power == tm2Power) {
                        tm1.setEqual((int) tm1.getEqual() + 1);
                        tm2.setEqual((int) tm2.getEqual() + 1);
                    }
                }
            }
            if (lines[0].equalsIgnoreCase("rank")) {
                ArrayList<Team> ranks =  GetRanks();
                int rankplace=1;
                for (Team t : ranks) {
                    System.out.println(rankplace+". " + t.getName());
                    rankplace++;
                }
            }
            if (lines[0].equalsIgnoreCase("end")) {
                  System.out.println("end");
            }
            myline = in.nextLine();
        }
        in.close();

    }

    public static Player GetPlayer(int id) {
        for (Player p : allPlayers) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public static Team GetTeam(int id) {
        for (Team t : allTeams) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public static ArrayList<Team> GetRanks() {
        ArrayList<Team> result = allTeams;
        int n=(int)allTeams.stream().count();
        int i,j;
        for ( i = 0; i < n - 1; i++) {
            for (j = 0; j < n - i - 1; j++) {
                if (result.get(j).getWin() < result.get(j+1).getWin()) {
                    Collections.swap(result, j, j+1);
                } else if (result.get(j).getWin() == result.get(j+1).getWin()) {
                    if (result.get(j).getLoose() > result.get(j+1).getLoose()) {
                        Collections.swap(result, j, j+1);
                    }
                }
            }
        }
        return result;
    }


}
