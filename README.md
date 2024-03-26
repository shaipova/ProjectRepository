# MyTomatoTrain
### Приложение на основе техники управления временем "метод помидора" 

Совмещает планирование дел на день/неделю с "забронированным" под каждую из задач временем по методу помидора. 
Планирование помогает грамотно распределить задачи в течение дня или недели, а метод помидора - держать фокус на их выполнении. 
Поддерживает темную и светлую темы

## Фичи 

### Расписание 

Первый экран приложения. 
Отсюда можно запустить "пустой" помидор без привязки к конкретной задаче, открыть экран гибкого расписания на день или неделю, открыть экран создания новой задачи
Экран гибкого расписания открывается по клику на виджеты, где отображается сколько ежедневных/еженедельных задач создано, сколько выполнено, и их общее время

<div id="badges">
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476263.png" width="200" height="400"/>
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476491.png" width="200" height="400"/>
</div>

##
### Создание новой задачи 


Каждая задача имеет название, цвет, количество помидоров (1 помидор это 25 минут) и категорию: ежеденевные задачи или еженедельные.
<div id="badges">
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476272.png" width="200" height="400"/>
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476304.png" width="200" height="400"/>
</div>

##
### Список задач на день и на неделю 

Повторяющиеся задачи на каждый день или на неделю. Отсюда можно запустить таймер для задачи 
<div id="badges">
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476540.png" width="200" height="400"/>
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476601.png" width="200" height="400"/>
</div>

##
### Таймер 

Отсчитывает 1 помидор. Можно поставить на паузу, продолжить выполнение или сбросить 
Цвет анимации соответствует цвету текущей задачи 
В верхней части экрана есть карточка задачи с прогрессом выполнения: сколько помидоров сделано, сколько времени осталось 
В горизонтальной ориентации таймер работает с другой анимацией (просто так) 

<div id="badges">
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476482.png" width="200" height="400"/>
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/Screenshot_1711476587.png" width="200" height="400"/>
  <img src="https://github.com/shaipova/ProjectRepository/blob/master/timer_gif.gif" width="200" height="400"/>
</div>


## Стек 

* Single Activity Application 
* Kotlin Coroutines + Flow
* Dagger2 
* Room
* Retrofit
* GSON
* Кастомная навигация

1 UI тест для сценария создания новой задачи 
5 Unit тестов для классов Room Dao, Repository, Worker, TimerHelper, Util

## Будущие фичи 

* Работа таймера в foreground сервисе
* Управление таймером с карточки задачи
* Локальный сбор и отображение статистики для юзера по выполненным задачам
* Добавить отсчет времени перерыва между помидорами
* Переход на MVVM
