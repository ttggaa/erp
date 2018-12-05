package com.stosz.purchase.fsm.purchaseReturnFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseReturnedItemService;
import com.stosz.purchase.service.PurchaseReturnedService;
import com.stosz.purchase.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/12/21 20:44
 */
@Component
public class PurchaseReturnAgreeAfter extends IFsmHandler<PurchaseReturned> {

    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;

    @Resource
    private PurchaseItemService purchaseItemService;

    @Resource
    private PurchaseService purchaseService;



    @Resource
    private PurchaseReturnedService purchaseReturnedService;

   /* @Resource
    private IFinanceService financeService;*/

    private final Logger logger = LoggerFactory.getLogger(PurchaseReturnAgreeAfter.class);

    @Override
    public void execute(PurchaseReturned purchaseReturned, EventModel event) {

        List<PurchaseReturnedItem> purchaseReturnedItems = purchaseReturnedItemService.queryListByReturnId(purchaseReturned.getId());
        if (ReturnedTypeEnum.INTRANSIT.name().equals(purchaseReturned.getType())) {// 在途退货
            if(purchaseReturned.getAmount()==purchaseReturned.getPayAmount()){
                Payable addPayAble = purchaseReturnedService.getAddPayAble(purchaseReturned, purchaseReturnedItems);
               // financeService.addPayable(new com.stosz.finance.ext.dto.request.AddPayable());
            }
        }
    }
}