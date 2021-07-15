package com.github.leuvaarden.sample.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Data
public class CurrencyResponse {
    private LocalDate date;
    @NotNull
    @JsonAlias({"aed", "afn", "all", "amd", "ang", "aoa", "ars", "aud", "awg", "azn", "bam", "bbd", "bch", "bdt", "bgn", "bhd", "bif", "bmd", "bnd", "bob", "brl", "bsd", "btc", "btn", "bwp", "byn", "bzd", "cad", "cdf", "chf", "clf", "clp", "cnh", "cny", "cop", "crc", "cup", "cve", "czk", "djf", "dkk", "dop", "dzd", "ecs", "ern", "etb", "eth", "eur", "fjd", "gbp", "gel", "ghs", "gip", "gmd", "gnf", "gqe", "gtq", "gyd", "hkd", "hnl", "hrk", "htg", "huf", "idr", "ils", "inr", "iqd", "irr", "isk", "jmd", "jod", "jpy", "kes", "kgs", "khr", "kmf", "kpw", "krw", "kwd", "kyd", "kzt", "lak", "lbp", "lkr", "lrd", "lsl", "ltc", "lyd", "mad", "mdl", "mga", "mkd", "mmk", "mnt", "mop", "mru", "mur", "mvr", "mwk", "mxn", "myr", "mzm", "mzn", "nad", "ngn", "nio", "nok", "npr", "nzd", "omr", "pab", "pen", "pgk", "php", "pkr", "pln", "pyg", "qar", "ron", "rsd", "rub", "rwf", "sar", "sbd", "scr", "sdg", "sek", "sgd", "shp", "sll", "sos", "srd", "ssp", "std", "stn", "svc", "syp", "szl", "thb", "tjs", "tmt", "tnd", "top", "try", "ttd", "twd", "tzs", "uah", "ugx", "usd", "uyu", "uzs", "ves", "vef", "vnd", "vuv", "wst", "xaf", "xag", "xcd", "xof", "xpd", "xpf", "xpt", "yer", "zar", "zmw"})
    private Map<Currency, Double> payload;
}
