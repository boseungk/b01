package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.CompanyDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.CompanyService;

import javax.validation.Valid;

@Controller
@RequestMapping("/company")
@Log4j2
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<CompanyDTO> responseDTO = companyService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/register")
    public void registerGET() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid CompanyDTO companyDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("company POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/company/register";
        }

        log.info(companyDTO);

        Long bno = companyService.register(companyDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/company/list";
    }


    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        CompanyDTO companyDTO = companyService.readOne(bno);

        log.info(companyDTO);

        model.addAttribute("dto", companyDTO);

    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid CompanyDTO companyDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        log.info("company modify post......." + companyDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", companyDTO.getBno());

            return "redirect:/company/modify?" + link;
        }

        companyService.modify(companyDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", companyDTO.getBno());

        return "redirect:/company/read";
    }


    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + bno);

        companyService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/company/list";

    }
}
