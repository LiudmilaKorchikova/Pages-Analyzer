@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.utils.NamedRoutes
@import hexlet.code.utils.DateFormatter

@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <h1>Список Url</h1>
    <div>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>URL</th>
                <th>Дата создания</th>
                <th>Последняя проверка</th>
                <th>Код ответа</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getUrls())
                <tr>
                    <td>${url.getId()}</td>
                    <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                    <td>${DateFormatter.format(url.getCreatedAt())}</td>

                    @if(page.getLastChecks().containsKey(url.getId()))  <!-- Если последняя проверка существует -->
                        <td>${DateFormatter.format(page.getLastChecks().get(url.getId()).getCreatedAt())}</td>
                        <td>${page.getLastChecks().get(url.getId()).getStatusCode()}</td>
                    @else  <!-- Если проверок нет, выводим сообщение -->
                        <td>Нет данных</td>
                        <td>---</td>
                    @endif
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`
)