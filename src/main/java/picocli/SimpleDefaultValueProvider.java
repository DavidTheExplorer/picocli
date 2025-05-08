package picocli;

import picocli.CommandLine.IDefaultValueProvider;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SimpleDefaultValueProvider implements IDefaultValueProvider
{
    private final Map<String, ValueProvider> options;

    private SimpleDefaultValueProvider(Map<String, ValueProvider> options) {
        this.options = options;
    }

    @Override
    public String defaultValue(ArgSpec argSpec) throws Exception {

        if(argSpec.isOption())
            return getDefaultValue(this.options, ((OptionSpec) argSpec).longestName());

        return null;
    }

    private <K> String getDefaultValue(Map<K, ValueProvider> map, K key) {
        ValueProvider provider = map.get(key);

        return provider == null ? null : provider.get();
    }



    public static class Builder {
        private final Map<String, ValueProvider> options = new HashMap<String, ValueProvider>();

        public Builder forOption(String option, final String value) {
            return forOption(option, new ValueProvider() {
                @Override
                public String get() {
                    return value;
                }
            });
        }

        public Builder forOption(String option, ValueProvider provider) {
            this.options.put(option, provider);
            return this;
        }

        public SimpleDefaultValueProvider build() {
            return new SimpleDefaultValueProvider(this.options);
        }
    }

    public interface ValueProvider
    {
        String get();
    }
}
