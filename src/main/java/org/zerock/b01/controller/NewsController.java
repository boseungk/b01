package org.zerock.b01.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.NewsDTO;
import org.zerock.b01.dto.NewsListNewsReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.NewsService;

import javax.validation.Valid;

@Controller
@RequestMapping("/news")
@Log4j2
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

//        PageResponseDTO<NewsDTO> responseDTO = newsService.list(pageRequestDTO);
        PageResponseDTO<NewsListNewsReplyCountDTO> responseDTO = newsService.listWithNewsReplyCount(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost(@Valid NewsDTO newsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("news POST register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return "redirect:/news/register";
        }

        log.info(newsDTO);

        Long bno = newsService.register(newsDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/news/list";
    }


    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){


        log.info("news read or modify get.......");

        NewsDTO newsDTO = newsService.readOne(bno);

        log.info(newsDTO);

        model.addAttribute("dto", newsDTO);

    }

    @PostMapping("/modify")
    public String modify( PageRequestDTO pageRequestDTO,
                          @Valid NewsDTO newsDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        log.info("news modify post......." + newsDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", newsDTO.getBno());

            return "redirect:/news/modify?"+link;
        }

        int loginValue = newsService.modify(newsDTO);

        if(loginValue == 1){
            log.info("비밀번호가 다릅니다.");

            redirectAttributes.addFlashAttribute("login", "denied");

            redirectAttributes.addAttribute("bno", newsDTO.getBno());

            return "redirect:/news/modify";
        }

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", newsDTO.getBno());

        return "redirect:/news/read";
    }


    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + bno);

        newsService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/news/list";

    }


}
