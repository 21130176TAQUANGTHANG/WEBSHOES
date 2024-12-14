package Signature;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KeyDeletionScheduler {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleKeyDeletion(String userId) {
        Runnable task = () -> {
            DbSecurity db = new DbSecurity();
            db.deleteKey(userId); // Gọi phương thức xóa key
            System.out.println("Key của người dùng " + userId + " đã bị xóa.");
        };

        // Lập lịch xóa sau 1 phút
        scheduler.schedule(task, 1, TimeUnit.MINUTES);
    }
}

