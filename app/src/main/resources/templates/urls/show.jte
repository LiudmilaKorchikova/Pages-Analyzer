@template.layout.page(
    content = @`
        <h1>Список URL</h1>

        <!-- Если есть flash-сообщение, показываем его -->
        <div class="alert alert-info" th:if="${flashMessage}" role="alert">
            ${flashMessage}
        </div>

        <ul>
            <!-- Выводим все URL -->
            @for (url in urls) {
                <li><a href="{{url}}">{{url}}</a></li>
            }
        </ul>

        <a href="/add-url" class="btn btn-primary">Добавить новый URL</a>
    `
)