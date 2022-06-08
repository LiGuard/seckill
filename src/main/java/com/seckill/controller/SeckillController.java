package com.seckill.controller;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillResult;
import com.seckill.dto.SuccessExecution;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 描述: 秒杀web
 * Aouthor ljw
 * createTime 2018-01-06 16:51
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
            List<Seckill> seckills = seckillService.queryAllSeckill(0, 30);
        model.addAttribute("list", seckills);

        return "list";
    }


    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable(value = "seckillId") Long seckillId, Model model){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.querySeckillById(seckillId);
        if(seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SuccessExecution> execute(@PathVariable(value = "seckillId") Long seckillId,
                                                   @PathVariable(value = "md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone){

        if(userPhone == null){
            return new SeckillResult<SuccessExecution>(false, "请您先注册账号");
        }

        try {
            SuccessExecution successExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<SuccessExecution>(true, successExecution);
        } catch (RepeatKillException e){
            logger.error(e.getMessage(), e);
            SuccessExecution successExecution = new SuccessExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SuccessExecution>(false, successExecution);
        } catch (SeckillCloseException e){
            logger.error(e.getMessage(), e);
            SuccessExecution successExecution = new SuccessExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SuccessExecution>(false, successExecution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SuccessExecution successExecution = new SuccessExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SuccessExecution>(false, successExecution);
        }
    }


    @RequestMapping(value = "/time/now", method = RequestMethod.GET,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }

}