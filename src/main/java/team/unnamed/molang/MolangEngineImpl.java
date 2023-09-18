package team.unnamed.molang;

import team.unnamed.molang.parser.ast.Expression;
import team.unnamed.molang.runtime.ExpressionEvaluator;
import team.unnamed.molang.runtime.binding.StorageBinding;
import team.unnamed.molang.parser.MolangParser;
import team.unnamed.molang.parser.ParseException;

import javax.script.Bindings;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

final class MolangEngineImpl implements MolangEngine {

    private final MolangParser parser = MolangParser.parser();

    private final Map<String, Object> bindings;

    MolangEngineImpl(MolangEngine.Builder builder) {
        this.bindings = builder.bindings;
    }

    private Bindings createBindings() {
        Bindings bindings = new SimpleBindings();
        bindings.putAll(this.bindings);

        // temporal storage
        StorageBinding temp = new StorageBinding();
        bindings.put("temp", temp);
        return bindings;
    }

    @Override
    public Object eval(List<Expression> expressions) {
        Bindings bindings = createBindings();
        ExpressionEvaluator evaluator = new ExpressionEvaluator(bindings);
        Object lastResult = 0;

        for (Expression expression : expressions) {
            lastResult = expression.visit(evaluator);
            Object returnValue = evaluator.popReturnValue();
            if (returnValue != null) {
                lastResult = returnValue;
                break;
            }
        }

        return lastResult;
    }

    @Override
    public Object eval(Reader reader) throws ScriptException {
        try {
            return eval(parser.parse(reader));
        } catch (IOException e) {
            throw new ScriptException(e);
        }
    }

    @Override
    public List<Expression> parse(Reader reader) throws ParseException {
        return parser.parse(reader);
    }

}
