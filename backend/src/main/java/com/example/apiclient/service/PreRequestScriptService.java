package com.example.apiclient.service;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PreRequestScriptService {

    public Map<String, Object> runPreRequestScript(String script, Map<String, Object> context) throws Exception {
        try (Context polyglot = Context.create("js")) {
            Value bindings = polyglot.getBindings("js");
            bindings.putMember("context", context);

            String wrappedScript = "function run(context) {" + script + "}; run(context);";

            Value result = polyglot.eval("js", wrappedScript);

            if (result.hasMembers()) {
                return result.as(Map.class);
            } else {
                return context;
            }
        } catch (Exception e) {
            throw new Exception("Error executing pre-request script: " + e.getMessage(), e);
        }
    }
}
