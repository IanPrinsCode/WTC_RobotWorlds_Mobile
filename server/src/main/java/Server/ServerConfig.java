package Server;

import org.apache.commons.cli.*;
import org.json.JSONObject;

public class ServerConfig {

    private final Integer visibility;
    private final Integer reload;
    private final Integer repair;
    private final Integer mine;
    private final Integer shields;
    private Integer size;
    private Integer port;
    private Integer apiPort;
    private String obstacles;
    private boolean output;


    public ServerConfig(JSONObject object) {

        this.visibility = (Integer) object.get("visibility");
        this.reload = (Integer) object.get("reload");
        this.repair = (Integer) object.get("repair");
        this.mine = (Integer) object.get("mine");
        this.shields = (Integer) object.get("shields");
        this.size = (Integer) object.get("size");
        this.port = (Integer) object.get("port");
        this.obstacles = (String) object.get("obstacles");
        this.apiPort = (Integer) object.get("apiport");
        this.output = true;
    }

    public void setConfigFromArgs(String[] args) throws ParseException {
        Options options = new Options();

        options.addOption(new Option("p","port",true,"Port number"));
        options.addOption(new Option("h","http",true,"HTTP Port number"));
        options.addOption(new Option("s","size",true,"Size of world"));
        options.addOption(new Option("o","obstacles",true,"Number of Obstacles"));
        options.addOption(new Option("z","obstacles",false,"Number of Obstacles"));

        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("p")) {
            this.port = Integer.parseInt(cmd.getOptionValue("port"));
        }

        if (cmd.hasOption("h")) {
            this.apiPort = Integer.parseInt(cmd.getOptionValue("http"));
        }

        if (cmd.hasOption("s")) {
            this.size = Integer.parseInt(cmd.getOptionValue("size"));
        }

        if (cmd.hasOption("o")) {
            this.obstacles = cmd.getOptionValue("obstacles");
        }

        if (cmd.hasOption("z")) {
            this.output = false;
        }
    }
    
    public Integer getVisibility(){return this.visibility;}

    public Integer getReload(){return this.reload;}

    public Integer getRepair(){return this.repair;}

    public Integer getShields(){return this.shields;}

    public Integer getMine(){return this.mine;}

    public Integer getSize(){return this.size;}

    public Integer getPort(){return this.port;}

    public String getObstacles(){return this.obstacles;}

    public boolean isOutput() { return output;}

    public Integer getApiPort() { return this.apiPort; }
}

