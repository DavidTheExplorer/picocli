package picocli;

import picocli.CommandLine.IDefaultValueProvider;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.util.HashMap;
import java.util.Map;

public class IDefaultValueProviderBuilder
{
    private final Map<String, ValueProvider> options = new HashMap<String, ValueProvider>();

    public IDefaultValueProviderBuilder forOption(String option, ValueProvider provider) {
        this.options.put(option, provider);
        return this;
    }

    public IDefaultValueProviderBuilder forOption(String option, final String value) {
        return forOption(option, new ValueProvider() {
            @Override
            public String get() {
                return value;
            }
        });
    }

    public IDefaultValueProvider build() {
        return new IDefaultValueProvider() {
            @Override
            public String defaultValue(ArgSpec argSpec) throws Exception {
                if(argSpec.isOption())
                    return getDefaultValue(options, ((OptionSpec) argSpec).longestName());

                return null;
            }
        };
    }

    private <K> String getDefaultValue(Map<K, ValueProvider> map, K key) {
        ValueProvider provider = map.get(key);

        return provider == null ? null : provider.get();
    }



    public interface ValueProvider {
        String get();
    }
}
