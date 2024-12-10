package day7;

import java.util.List;

public class ResultDay7 {
    protected boolean result;
    protected List<String> ops;
    protected Long targetValue;
    protected List<Integer> inputs;

    public ResultDay7(boolean result, List<String> ops, List<Integer> inputs, Long targetValue) {
        this.result = result;
        this.ops = ops;
        this.inputs = inputs;
        this.targetValue = targetValue;
    }

    public boolean isPossible() {
        return result;
    }

    public Long getTargetValue() {
        return targetValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("targetValue: " + targetValue + ", result = " + result);
        if (!result) {
            sb.append(" IMPOSSIBLE");
        } else {
            joinInputs(sb);
        }
        return sb.toString();
    }

    private void joinInputs(StringBuilder sb) {
        for (int i = 0; i < inputs.size(); i++) {
            if (i == inputs.size() - 1) {
                sb.append(inputs.get(i));
            } else {
                sb.append(inputs.get(i) + " " + ops.get(i) + " ");
            }
        }
    }

    public List<String> getOps() {
        return ops;
    }
}
