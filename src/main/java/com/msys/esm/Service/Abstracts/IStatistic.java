package com.msys.esm.Service.Abstracts;

import com.msys.esm.Core.Util.Enums.Platform;

import java.math.BigInteger;

public interface IStatistic {
    Platform getPlatform();

    BigInteger getSubscriberCount();

    BigInteger getViewCount();

    BigInteger getVideoCount();


}
