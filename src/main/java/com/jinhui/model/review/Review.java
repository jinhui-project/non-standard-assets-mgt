package com.jinhui.model.review;


import com.jinhui.model.Assets;
import com.jinhui.model.Users;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by jinhui on 2017/6/30.
 */
public class Review {
    private Long reviewId;
    private Users user;
    private String featureCode;
    private Assets asset;
    private State state;
    private String comment;
    private Date lastUpdatedTime;

    public enum State{
        Publish("发起评审"), Meeting("项目沟通会"),
        Business("预审-业务人员处理"), BizManager("预审-业务负责人审批"),
        Risk("预审-风控经理处理"), RiskManager("预审-风控负责人审批"),
        Product("预审-产品经理处理"), ProdManager("预审-产品负责人审批"),
        Review("评审"), Termination("结束评审");
        private String desc;
        State(String desc) {
            this.desc = desc;
        }
        public String getDesc(){return this.desc;}
    }

    private StateMachine<State, FeatureEvent> machine = null;

    /**
     * 触发状态转移动作
     * @return true:允许状态转移
     */
    public boolean fire(){
        return this.fireEvent(FeatureEvent.codeOf(featureCode));
    }
    private boolean fireEvent(FeatureEvent event){
        try {
            if(machine == null) {
                machine = buildSyncMachine();
                machine.addStateListener(new StateMachineListenerAdapter<State, FeatureEvent>() {
                    @Override
                    public void eventNotAccepted(Message<FeatureEvent> msg) {
                        FeatureEvent event = msg.getPayload();
                        StringBuilder appender = new StringBuilder();
                        appender.append("【").append(event.featureName()).append("】只能将资产从")
                                .append(event.reviewState()).append("修改为").append(event.nextState());
                        throw new IllegalArgumentException(appender.toString());
                    }

                    @Override
                    public void transitionEnded(Transition<State, FeatureEvent> transition) {
                        FeatureEvent event = transition.getTrigger().getEvent();
                        state = event.nextState();
                    }
                });
            }
            return machine.sendEvent(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Review(Integer userId, Integer assetId, String featureCode) {
        this.user = new Users();
        this.user.setId(userId);
        this.asset = new Assets();
        this.asset.setId(assetId);
        this.featureCode = featureCode;
    }

    public static Review initial(Integer assetId){
        Review review = new Review();
        review.asset = new Assets();
        review.asset.setId(assetId);
        review.state = State.Publish;
        return review;
    }

    public boolean isInitial(){
        return  this.reviewId == null && this.state == State.Publish;
    }

    public void reviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long reviewId() {
        return reviewId;
    }

    public Users user() {
        return user;
    }
    public void user(Integer uid){
        Users user = new Users();
        user.setId(uid);
        this.user = user;
    }

    public Assets asset() {
        return asset;
    }

    public State state() {
        return state;
    }

    public FeatureEvent featureEvent(){
        if(StringUtils.isEmpty(featureCode))
            return null;
        return FeatureEvent.codeOf(featureCode);
    }
    public void featureCode(String featureCode){
        this.featureCode = featureCode;
    }

    public String comment() {
        return comment;
    }
    public void comment(String comment){
        this.comment = comment;
    }

    public Date lastUpdatedTime() {
        return lastUpdatedTime;
    }

    public Review state(State state) {
        this.state = state;
        return this;
    }

    Review() {
        //for ORM
    }

    /**
     * 构建线程同步状态机
     */
    private StateMachine<State, FeatureEvent> buildSyncMachine() throws Exception {
        Assert.notNull(state,"状态不能为空");
        StateMachineBuilder.Builder<State, FeatureEvent> builder = StateMachineBuilder.builder();
        builder.configureConfiguration().withConfiguration()
                .taskExecutor(new SyncTaskExecutor())
                .beanFactory(new StaticListableBeanFactory())
                .autoStartup(true);
        //配置状态
        builder.configureStates()
                .withStates()
                .initial(state)
                .states(EnumSet.allOf(State.class));
        //配置状态转移
        StateMachineTransitionConfigurer<State, FeatureEvent> transition = builder.configureTransitions();
        for(FeatureEvent event:FeatureEvent.values()) {
            transition = transition.withExternal()
                    .source(event.reviewState())
                    .target(event.nextState())
                    .event(event)
                    .and();
        }
        return builder.build();
    }

}
