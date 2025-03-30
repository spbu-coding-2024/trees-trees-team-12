# Trees-team-12 Library

![example workflow](https://github.com/spbu-coding-2024/trees-trees-team-12/actions/workflows/build.yml/badge.svg)
[![CodeFactor](https://www.codefactor.io/repository/github/spbu-coding-2024/trees-trees-team-12/badge)](https://www.codefactor.io/repository/github/spbu-coding-2024/trees-trees-team-12)

## Библиотека, реализующая три вида бинарных деревьев поиска на Kotlin:
1. Стандартное бинарное дерево поиска (BST)
2. AVL-дерево
3. Красно-черное дерево (Red-Black Tree)

## Особенности
- Поддержка основных операций: вставка, удаление, поиск, итерация по паре ключ-значение
- Использование обобщений для работы с любыми типами данных
- Покрытие unit и property-based тестами

## Установка
1. Клонируйте репозиторий:
```bash
git clone git@github.com:spbu-coding-2024/trees-trees-team-12.git
```
2. Перейдите в директорию проекта
```bash
cd trees-trees-team-12
```
## Сборка и тестирование
1. Сборка с использованием Gradle
```bash
./gradlew build
```
2. Запуск тестов с использованием
```bash
./gradlew test
```
3. Очистка репозитория от артефактов сборки
```bash
./gradlew clean
```
- После тестов отчет об их успешном прохожении можно посмотреть, открыв файл `lib/build/reports/tests/index.html`
- После тестов отчет о тестовом покрытии jacoco можно посмотреть, открыв файл `lib/build/reports/jacoco/index.html`

## Руководство по использованию
- Создание дерева:
```kotlin
var bsTree: BSTree<KeyType, ValueType> = BSTree<KeyType, ValueType>()
```
```kotlin
var avlTree: AVLTree<KeyType, ValueType> = AVLTree<KeyType, ValueType>()
```
```kotlin
var rbTree: RBTree<KeyType, ValueType> = RBTree<KeyType, ValueType>()
```
Примеры операций:
- Вставка:
```kotlin
tree.insert(key: KeyType, value: ValueType)
```
- Удаление:
```kotlin
tree.delete(key: KeyType)
```
- Поиск:
```kotlin
var value: ValueType = tree.find(key: KeyType)
```
- Итерация по парам ключ-значение в дереве:
```kotlin
for (pair in tree) {
  println("Key: " + pair.first + "; Value: " + pair.second)
}
```


## Лицензия
[MIT](https://choosealicense.com/licenses/mit/)

## Авторы

- [Горлов Степан](https://github.com/Stepiiiiiiik)
- [Чепурных Даниил](https://github.com/Daniil-Chepurnikh)
- [Яковлев Николай](https://github.com/Nickovlev)
