import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRequest {
    @Test
    void getLaunchRequest() {
        Request r = new LaunchRequest("HAL", "test", 5, 5);
        assertEquals("{\"name\":\"HAL\",\"arguments\":[\"test\",5,5],\"command\":\"launch\"}",
                r.getRequest());
    }

    @Test
    void getStateRequest() {
        Request r = new StateRequest("HAL");
        assertEquals("{\"name\":\"HAL\",\"arguments\":[],\"command\":\"state\"}",
                r.getRequest());
    }

    @Test
    void getLookRequest() {
        Request r = new LookRequest("HAL");
        assertEquals("{\"name\":\"HAL\",\"arguments\":[],\"command\":\"look\"}",
                r.getRequest());
    }

    @Test
    void getMovementRequestForward() {
        Request r = new MovementRequest("HAL", true, 5);
        assertEquals("{\"name\":\"HAL\",\"arguments\":[5],\"command\":\"forward\"}",
                r.getRequest());
    }

    @Test
    void getMovementRequestBack() {
        Request r = new MovementRequest("HAL", false, 5);
        assertEquals("{\"name\":\"HAL\",\"arguments\":[5],\"command\":\"back\"}",
                r.getRequest());
    }

    @Test
    void getTurnRequestRight() {
        Request r = new TurnRequest("HAL", true);
        assertEquals("{\"name\":\"HAL\",\"arguments\":[\"right\"],\"command\":\"turn\"}",
                r.getRequest());
    }

    @Test
    void getTurnRequestLeft() {
        Request r = new TurnRequest("HAL", false);
        assertEquals("{\"name\":\"HAL\",\"arguments\":[\"left\"],\"command\":\"turn\"}",
                r.getRequest());
    }

    @Test
    void getRepairRequest() {
        Request r = new RepairRequest("HAL");
        assertEquals("{\"name\":\"HAL\",\"arguments\":[],\"command\":\"repair\"}",
                r.getRequest());
    }

    @Test
    void getReloadRequest() {
        Request r = new ReloadRequest("HAL");
        assertEquals("{\"name\":\"HAL\",\"arguments\":[],\"command\":\"reload\"}",
                r.getRequest());
    }

    @Test
    void getMineRequest() {
        Request r = new MineRequest("HAL");
        assertEquals("{\"name\":\"HAL\",\"arguments\":[],\"command\":\"mine\"}",
                r.getRequest());
    }

    @Test
    void getFireRequest() {
        Request r = new FireRequest("HAL");
        assertEquals("{\"name\":\"HAL\",\"arguments\":[],\"command\":\"fire\"}",
                r.getRequest());
    }
}
