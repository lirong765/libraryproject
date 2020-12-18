package com.rong.controller;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.BookInfo;
import com.rong.pojo.LendList;
import com.rong.pojo.ReaderCard;
import com.rong.service.BookInfoService;
import com.rong.service.LendListService;
import com.rong.service.ReaderService;
import com.rong.util.R;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class LendListController {

    @Autowired
    private LendListService lendListService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookInfoService bookInfoService;

    @GetMapping("/lendListIndex")
    public String lendListIndex(){
        return "lend/lendListIndex";
    }

    /**
     * 查询所有的列表
     * 1、request获取
     * 2、参数绑定
     * 3、对象绑定
     */
    @ResponseBody
    @RequestMapping("/lendListAll")
    public R lendListAll(Integer type, String cardnumber, String name, Integer status,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "15") Integer limit) {
        LendList info = new LendList();
        info.setType(type);

        //创建读者对象
        ReaderCard reader = new ReaderCard();
        reader.setCardnumber(cardnumber);
        //把以上对象交给info
        info.setReaderCard(reader);

        //图书对象
        BookInfo book = new BookInfo();
        book.setName(name);
        book.setStatus(status);
        info.setBookInfo(book);

        //分页查询所有的记录信息
        PageInfo pageInfo = lendListService.queryLendListAll(info, page, limit);
        return R.ok("ok", pageInfo.getTotal(), pageInfo.getList());
    }
    /**
     * 添加跳转
     */
    @GetMapping("/addLendList")
    public String addLendList(){
        return "lend/addLendList";
    }

    /**
     * 查询状态为未借出（status=1）的所有图书信息
     */
    @ResponseBody
    @RequestMapping("/bookStatusIs1")
    public R bookAll(BookInfo info, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "15") int limit){

        PageInfo<BookInfo> pageInfo = bookInfoService.bookStatusIs1(info, page, limit);
        return  R.ok("借出成功", pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 借书信息提交
     * 1、判断借阅号码是否存在
     * 2、可借的数据是否大于等于当前的借书量
     * 3、添加借书记录，同时改变书的状态信息
     * cardnumber:借书号码
     * ids:字符串 书id的集合
     */
    @ResponseBody
    @RequestMapping("/addLend")
    public R addLend(String cardnumber, String ids){
        //获取图书id的集合
        List<String> list = Arrays.asList(ids.split(","));
        //判断卡号是否存在
        ReaderCard reader = new ReaderCard();
        reader.setCardnumber(cardnumber);
        PageInfo<ReaderCard> pageInfo = readerService.queryReaderAll(reader, 1, 1);
        if(pageInfo.getList().size() == 0){
            return R.fail("卡号信息不存在");
        } else {
            //判断用户的可借书数是否大于等于当前要借数据
            ReaderCard readerCard = pageInfo.getList().get(0);
            int num = readerCard.getNumber();   //获取限制可借的书的数量
            //获取当前读者已经借了多少本
            int number = lendListService.queryBookNum(readerCard.getId());
            //判断
            if(num-number>=list.size()){
                //可以借书
                for (String bookId : list) {
                    LendList lendList = new LendList();
                    lendList.setReaderId(readerCard.getId());
                    lendList.setBookId(Integer.valueOf(bookId));
                    lendList.setLeadDate(new Date());
                    lendListService.andLendListSubmit(lendList);
                    //更变书的状态
                    BookInfo bookInfo = bookInfoService.queryBookInfoById(Integer.valueOf(bookId));
                    //设置书的状态
                    bookInfo.setStatus(1);
                    System.out.println(bookInfo.getStatus());
                    bookInfoService.updateBookSubmit(bookInfo);
                }
            }else {
                return R.fail("目前借书数量大于可借数量...");
            }
        }
        return R.ok();
    }

    /**
     * 删除借阅记录
     */
    @ResponseBody
    @RequestMapping("/deleteLendListByIds")
    public R deleteLendListByIds(String ids, String bookIds){

        List<String> list = Arrays.asList(ids.split(","));
        List<String> blist = Arrays.asList(bookIds.split(","));
        int num = lendListService.deleteLendListByIds(list, blist);

        return R.ok();
    }

    /**
     * 还书功能
     */
    @ResponseBody
    @RequestMapping("/backLendListByIds")
    public R backLendListByIds(String ids, String bookIds){

        List<String> list = Arrays.asList(ids.split(","));
        List<String> blist = Arrays.asList(bookIds.split(","));
        int num = lendListService.updateLendListSubmit(list, blist);

        return R.ok();
    }

    /**
     * 页面的跳转 异常还书
     */
    @GetMapping("/excBackBook")
    public String excBackBook(HttpServletRequest request, Model model){
        //获取借阅记录id
        String id = request.getParameter("id");
        String bid = request.getParameter("bookId");
        model.addAttribute("id", id);
        model.addAttribute("bid", bid);

        return "lend/excBackBook";
    }

    /**
     * 异常还书
     */
    @GetMapping
    @RequestMapping("/updateLendInfoSubmit")
    public R updateLendInfoSubmit(LendList lendList){
        lendListService.backBook(lendList);

        return R.ok();
    }

    /**
     * 查阅时间线
     */
    @RequestMapping("/queryLookBookList")
    public String queryLookBookList(String flag, Integer id, Model model){
        List<LendList> lendLists;
        if(flag.equals("book")){
            lendLists = lendListService.queryLookBookList(null, id);
        } else {
            lendLists = lendListService.queryLookBookList(id, null);
        }

        model.addAttribute("info", lendLists);
        return "lend/lookBookList";
    }

    /**
     * 查阅时间线
     */
    @RequestMapping("/queryLookBookList2")
    public String queryLookBookList2(HttpServletRequest request, Model model){
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("user");
        List<LendList> lendLists = lendListService.queryLookBookList(readerCard.getId(), null);
        model.addAttribute("info", lendLists);
        return "lend/lookBookList";
    }
}
