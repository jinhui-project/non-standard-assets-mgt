package com.jinhui.model.review;


/**
 * Created by jinhui on 2017/6/30.
 */
public enum FeatureEvent implements Feature {
    Zero {
        public String code() {
            return "LAUNCH_MEETING";
        }

        public String featureName() {
            return null;
        }

        public Review.State reviewState() {
            return Review.State.Publish;
        }

        public Review.State nextState() {
            return Review.State.Meeting;
        }
    },
    First {
        public String code() {
            return "PASS_MEETING";
        }

        public String featureName() {
            return "通过";
        }

        public Review.State reviewState() {
            return Review.State.Meeting;
        }

        public Review.State nextState() {
            return Review.State.Business;
        }
    },
    First_N {
        public String code() {
            return "NEARLY_MEETING";
        }

        public String featureName() {
            return "有条件通过";
        }

        public Review.State reviewState() {
            return Review.State.Meeting;
        }

        public Review.State nextState() {
            return Review.State.Business;
        }
    },
    First_R {
        public String code() {
            return "REJECT_MEETING";
        }

        public String featureName() {
            return "不通过";
        }

        public Review.State reviewState() {
            return Review.State.Meeting;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    First_T {
        public String code() {
            return "TERM_MEETING";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.Meeting;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Second {
        public String code() {
            return "WAIT_BIZ_MANAGER";
        }

        public String featureName() {
            return null;
        }

        public Review.State reviewState() {
            return Review.State.Business;
        }

        public Review.State nextState() {
            return Review.State.BizManager;
        }
    },
    Second_T {
        public String code() {
            return "TERM_BIZ";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.Business;
        }

        public Review.State nextState() {
            return Review.State.BizManager;
        }
    },
    Third {
        public String code() {
            return "PASS_BIZ_MANAGER";
        }

        public String featureName() {
            return "通过";
        }

        public Review.State reviewState() {
            return Review.State.BizManager;
        }

        public Review.State nextState() {
            return Review.State.Risk;
        }
    },
    Third_N {
        public String code() {
            return "NEARLY_BIZ_MANAGER";
        }

        public String featureName() {
            return "有条件通过";
        }

        public Review.State reviewState() {
            return Review.State.BizManager;
        }

        public Review.State nextState() {
            return Review.State.Risk;
        }
    },
    Third_R {
        public String code() {
            return "REJECT_BIZ_MANAGER";
        }

        public String featureName() {
            return "不通过";
        }

        public Review.State reviewState() {
            return Review.State.BizManager;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Third_T {
        public String code() {
            return "TERM_BIZ_MANAGER";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.BizManager;
        }

        public Review.State nextState() {
            return Review.State.Risk;
        }
    },
    Fourth {
        public String code() {
            return "WAIT_RISK_MANAGER";
        }

        public String featureName() {
            return null;
        }

        public Review.State reviewState() {
            return Review.State.Risk;
        }

        public Review.State nextState() {
            return Review.State.RiskManager;
        }
    },
    Fourth_T {
        public String code() {
            return "TERM_RISK";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.Risk;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Fifth{
        public String code() {
            return "PASS_RISK_MANAGER";
        }

        public String featureName() {
            return "通过";
        }

        public Review.State reviewState() {
            return Review.State.RiskManager;
        }

        public Review.State nextState() {
            return Review.State.Product;
        }
    },
    Fifth_N{
        public String code() {
            return "NEARLY_RISK_MANAGER";
        }

        public String featureName() {
            return "有条件通过";
        }

        public Review.State reviewState() {
            return Review.State.RiskManager;
        }

        public Review.State nextState() {
            return Review.State.Product;
        }
    },
    Fifth_R{
        public String code() {
            return "REJECT_RISK_MANAGER";
        }

        public String featureName() {
            return "不通过";
        }

        public Review.State reviewState() {
            return Review.State.RiskManager;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Fifth_T {
        public String code() {
            return "TERM_RISK_MANAGER";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.RiskManager;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Sixth {
        public String code() {
            return "WAIT_PROD_MANAGER";
        }

        public String featureName() {
            return "等待产品经理审核";
        }

        public Review.State reviewState() {
            return Review.State.Product;
        }

        public Review.State nextState() {
            return Review.State.ProdManager;
        }
    },
    Sixth_T {
        public String code() {
            return "TERM_PROD";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.Product;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Seventh {
        public String code() {
            return "PASS_PROD_MANAGER";
        }

        public String featureName() {
            return "通过";
        }

        public Review.State reviewState() {
            return Review.State.ProdManager;
        }

        public Review.State nextState() {
            return Review.State.Review;
        }
    },
    Seventh_N {
        public String code() {
            return "NEARLY_PROD_MANAGER";
        }

        public String featureName() {
            return "有条件通过";
        }

        public Review.State reviewState() {
            return Review.State.ProdManager;
        }

        public Review.State nextState() {
            return Review.State.Review;
        }
    },
    Seventh_R {
        public String code() {
            return "REJECT_PROD_MANAGER";
        }

        public String featureName() {
            return "不通过";
        }

        public Review.State reviewState() {
            return Review.State.ProdManager;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Seventh_T {
        public String code() {
            return "TERM_PROD_MANAGER";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.ProdManager;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Eighth{
        public String code() {
            return "REVIEW_PASS";
        }

        public String featureName() {
            return "通过";
        }

        public Review.State reviewState() {
            return Review.State.Review;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Eighth_R{
        public String code() {
            return "REVIEW_REJECT";
        }

        public String featureName() {
            return "不通过";
        }

        public Review.State reviewState() {
            return Review.State.Review;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Eighth_D{
        public String code() {
            return "REVIEW_DISCUSSING";
        }

        public String featureName() {
            return "待议";
        }

        public Review.State reviewState() {
            return Review.State.Review;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Eighth_N{
        public String code() {
            return "REVIEW_NEARLY";
        }

        public String featureName() {
            return "有条件通过";
        }

        public Review.State reviewState() {
            return Review.State.Review;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    },
    Eighth_T{
        public String code() {
            return "TERM_REVIEW";
        }

        public String featureName() {
            return "终止";
        }

        public Review.State reviewState() {
            return Review.State.Review;
        }

        public Review.State nextState() {
            return Review.State.Termination;
        }
    };

    public static FeatureEvent codeOf(String code){
        for(FeatureEvent event: FeatureEvent.values()){
            if(code.equals(event.code()))
                return event;
        }
        throw new IllegalArgumentException("未知功能");
    }

    public static FeatureEvent nextStateOf(Review.State state){
        for(FeatureEvent event: FeatureEvent.values()){
            if(state==event.reviewState())
                return event;
        }
        throw new IllegalArgumentException("未知功能");
    }
}
