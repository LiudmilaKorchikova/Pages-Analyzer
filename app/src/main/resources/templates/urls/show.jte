@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.utils.NamedRoutes
@import hexlet.code.utils.DateFormatter

@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div>
        <h1>Сайт: ${page.getUrl().getName()}</h1>
    </div>
    <div>
        <table class="table table-striped table-bordered">
            <tbody>
            <tr>
                <td><strong>ID</strong></td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td><strong>URL</strong></td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td><strong>Added at</strong></td>
                <td>${DateFormatter.format(page.getUrl().getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div>
        <form action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}" method="POST">
            <button type="submit" class="btn btn-primary">Check</button>
        </form>
    </div>
    <div>
        @if(!page.getUrlChecks().isEmpty())
            <h3>Checks results:</h3>
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Check date</th>
                    <th>Response code</th>
                    <th>Title</th>
                    <th>H1</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                @for(var check : page.getUrlChecks())
                    <tr>
                        <td>${check.getId()}</td>
                        <td>${DateFormatter.format(check.getCreatedAt())}</td>
                        <td>
                            @if(check.getStatusCode() == 200)
                                <span class="badge badge-success">${check.getStatusCode()}</span>
                            @elseif(check.getStatusCode() >= 400)
                                <span class="badge badge-danger">${check.getStatusCode()}</span>
                            @else
                                <span class="badge badge-warning">${check.getStatusCode()}</span>
                            @endif
                        </td>
                        <td>${check.getTitle() != null ? check.getTitle() : "N/A"}</td>
                        <td>${check.getH1() != null ? check.getH1() : "N/A"}</td>
                        <td>${check.getDescription() != null ? check.getDescription() : "N/A"}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        @endif
    </div>
`
)
