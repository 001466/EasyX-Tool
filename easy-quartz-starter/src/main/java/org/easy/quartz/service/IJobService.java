package org.easy.quartz.service;

import org.easy.quartz.entity.QuartzJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface IJobService {



    QuartzJob saveJob(String jobName, String jobGroup, String description, Job job, String cronExpression, String triggerName, JobDataMap jobDataMap) throws SchedulerException;
    QuartzJob saveJob(String jobName, String jobGroup, String description, Job job, Long delayTime, final TimeUnit unit, String triggerName, JobDataMap jobDataMap) throws SchedulerException;


    /**
     * 新增job
     * @param quartz
     * @return
     */
    QuartzJob saveJob(QuartzJob quartz) throws SchedulerException, ClassNotFoundException, IllegalAccessException, InstantiationException;

    /**
     * 触发job
     * @param jobName
     * @param jobGroup
     * @return
     */
    JobKey triggerJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 暂停job
     * @param jobName
     * @param jobGroup
     * @return
     */
    JobKey pauseJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 恢复job
     * @param jobName
     * @param jobGroup
     * @return
     */
    JobKey resumeJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 移除job
     * @param jobName
     * @param jobGroup
     * @return
     */
    JobKey removeJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 移除job
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    List<JobKey> removeJob(String jobGroup) throws SchedulerException;
}
