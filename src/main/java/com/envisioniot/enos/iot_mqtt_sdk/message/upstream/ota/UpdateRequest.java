package com.envisioniot.enos.iot_mqtt_sdk.message.upstream.ota;

import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.DeliveryTopicFormat;
import com.envisioniot.enos.iot_mqtt_sdk.core.internals.constants.MethodConstants;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.BaseMqttRequest;
import com.google.common.collect.Maps;

import java.util.Map;

public class UpdateRequest extends BaseMqttRequest<UpdateRequestResponse> {

    /**
     *
     */
    private static final long serialVersionUID = 1342670380317469907L;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BaseMqttRequest.Builder<Builder, UpdateRequest> {

        @Override
        protected String createMethod() {
            return MethodConstants.PROGRESS_REPORT;
        }

        @Override
        protected Object createParams() {
            Map<String, String> map = Maps.newHashMap();
            return map;
        }

        @Override
        protected UpdateRequest createRequestInstance() {
            return new UpdateRequest();
        }
    }

    @Override
    public Class<UpdateRequestResponse> getAnswerType() {
        return UpdateRequestResponse.class;
    }

    @Override
    protected String _getPK_DK_FormatTopic() {
        return DeliveryTopicFormat.UPDATE_REQUEST_TOPIC_FMT;
    }

    private UpdateRequest() {
    }
}