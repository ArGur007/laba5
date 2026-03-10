public class RunResult {
        private final String parameter;
        private final double value;

        public RunResult(String parameter, double value) {
            this.parameter = parameter;
            this.value = value;
        }

        @Override
        public String toString() {
            return parameter + ": " + value;
        }
    }

