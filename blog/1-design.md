# Scrava: a wannabe Scrapy in Java

There is not much debate that Python is the first in class language for scraping with it's ecosystem of richly featured frameworks (`Scrapy`, `MechanicalSoup`, ...) built on top of robust tools (`bs4`, `requests`, `lxml`, `parsel`, ...) and integrations (`Selenium`, `Playwright`, `undetected-chromedriver`, ...). While not the only choice, for instance we can stick to the official ["scheme for the browser"](https://auth0.com/blog/a-brief-history-of-javascript/) as known as JavaScript or to a language we're already familiar with like master shipper [Pieter Levels does]() with PHP, Python is the most commonly used tool for the job for good reasons.

Nonetheless, [...]. To scratch my itch of scraping within Java and to use an excuse for a deeper learning of the language while remaining close to a domain I'm familiar with, I've been attempting to reproduce some core features of Scrapy into a framework. This is a hobby project and obviously does not claim to be a peer to Scrapy whose development runs deep as an exhaustively combat-tested and production-ready framework. 

## Features

The goal is having the following features ~~copied~~ implemented and (mostly) crash-proofed :

- User-defined project repositories isolated from the core library yet allowing for extensibility (IDEA: separation of concern);

- Control over CLI commands for generating project repos & spiders and running crawls & tests;

- Modular and customizable architecture for middlewares, downloader and pipelines;

- Customizeable defaults and modules from a configurations file;

- Defined items parsed through pipelines.

From the gate, it should include the following module implementations

- JSON, CSV and PostgreSQL pipelines;

- Jsoup and Playwright processors;

- Async processors;

- Cacheing downloader.

Some would-be nice to have extras would be :

- Spider interface with common response methods across processors, such a chainable `.css`, `.xpath`, `.re()` getters, 

- Typed items throwing or defaulting;

## Objects and methods

Notes: Number for classes, number with letter for class's methods. In examples, values in brackets like `<___>` are user-defined inputs. 

1. `class CLI` : main container and CLI entry point;

1a. `public static void main(String[] args)`: uses CLI args to execute appropriate method or interactive CLI if no args. Example: run `java -jar Scrava.jar <args>` in terminal;

1b. `public static void crawl(String[] spiders)`: launch spiders (through `Engine`). Example: use args `crawl ,<spider1> <spider2> <...>` or `crawl all`;

1c. `public static void startProject(String name, String[] options)`:initiate a project folder from template with optional settings. Example: use args `startproject <name> path:<path>`

1d. `public static void genSpider(String name, String[] options)`: create a spider file from template with optional settings. Example: use args `genspider <name> <website or TLD> template:<template>`

- Engine: 

## Code flow

