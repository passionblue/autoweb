package com.jtrend.session;

import java.util.Date;

public interface SessionStorable
{
    Date getCreatedTime();
    int  getScope();
}
