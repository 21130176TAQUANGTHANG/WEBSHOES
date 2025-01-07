package Signature;

public class VerifyResult {
    private boolean valid;

    public VerifyResult(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}
