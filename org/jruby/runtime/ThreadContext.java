package org.jruby.runtime;

import java.util.HashMap;
import java.util.Map;

import org.ablaf.ast.INode;
import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyModule;
import org.jruby.RubyObject;
import org.jruby.RubyThread;
import org.jruby.ast.MultipleAsgnNode;
import org.jruby.ast.ZeroArgNode;
import org.jruby.evaluator.AssignmentVisitor;
import org.jruby.evaluator.Evaluator;
import org.jruby.exceptions.ArgumentError;
import org.jruby.exceptions.NextJump;
import org.jruby.exceptions.RaiseException;
import org.jruby.exceptions.RedoJump;
import org.jruby.exceptions.ReturnException;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.util.RubyStack;
import org.jruby.util.collections.IStack;

/**
 * @author jpetersen
 * @version $$Revision$$
 */
public class ThreadContext {
    private final Ruby ruby;
    
    private Evaluator evaluator;
    
    private BlockStack blockStack;
    private RubyStack dynamicVarsStack;
    
    /* Thread related stuff */
    private RubyThread currentThread;
    private Map localVariables;

    /**
     * Constructor for Context.
     */
    public ThreadContext(Ruby ruby) {
        this.ruby = ruby;

        this.evaluator = new Evaluator(ruby);

        this.blockStack = new BlockStack(ruby);
        this.dynamicVarsStack = new RubyStack();
        
        this.localVariables = new HashMap();
        
        pushDynamicVars();
    }
    
    public BlockStack getBlockStack() {
        return blockStack;
    }
    
    public RubyStack getDynamicVarsStack() {
        return dynamicVarsStack;
    }
    
    public Map getCurrentDynamicVars() {
        return (Map)dynamicVarsStack.peek();
    }
    
    public void pushDynamicVars() {
        dynamicVarsStack.push(new HashMap());
    }
    
    public Evaluator getEvaluator() {
        return evaluator;
    }
    /**
     * Returns the currentThread.
     * @return RubyThread
     */
    public RubyThread getCurrentThread() {
        return currentThread;
    }

    /**
     * Returns the localVariables.
     * @return Map
     */
    public Map getLocalVariables() {
        return localVariables;
    }

    /**
     * Sets the currentThread.
     * @param currentThread The currentThread to set
     */
    public void setCurrentThread(RubyThread currentThread) {
        this.currentThread = currentThread;
    }

}